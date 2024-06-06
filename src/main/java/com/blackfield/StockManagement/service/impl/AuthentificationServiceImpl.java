package com.blackfield.StockManagement.service.impl;

import com.blackfield.StockManagement.bean.AuthenticationRequestBean;
import com.blackfield.StockManagement.bean.AuthenticationResponseBean;
import com.blackfield.StockManagement.domain.TokensDomain;
import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.jwt.JwtUtil;
import com.blackfield.StockManagement.mapper.UserMapper;
import com.blackfield.StockManagement.repository.TokensRepository;
import com.blackfield.StockManagement.service.AuthentificationService;
import com.blackfield.StockManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthentificationServiceImpl implements AuthentificationService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final TokensRepository tokensRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseBean authenticateByLoginAndPassword(AuthenticationRequestBean request) {
        System.out.println("Le login est :=="+request.getLogin());
        UsersDomain userDto = userService.findByLogin(request.getLogin(), "fr");

        boolean response = comparePasswordUserForAuthenticate(request.getPassword(), userDto.getPassword());

        validFieldAuthenticate(response, userDto);

        String jwtToken = jwtUtil.generateToken(userDto);
        revokeAllUserToken(userDto);
        saveUserToken(userDto, jwtToken);
        return AuthenticationResponseBean.builder()
                .accessToken(jwtToken)
                .message("Vous êtes maintenant connecté à la plateforme").build();
    }

    public boolean comparePasswordUserForAuthenticate(String rawPassword, String passwordEncrypt) {
        return passwordEncoder.matches(rawPassword, passwordEncrypt);
    }

    private void validFieldAuthenticate(boolean response, UsersDomain userDto) {
        if (!response) {
            throw new InvalidOperationException("Nom d'utililisateur ou mot de passe incorrect");
        }

        if (!userDto.isActiver()) {
            throw new InvalidOperationException("Votre compte n'est pas actif");
        }
    }

    private void saveUserToken(UsersDomain user, String jwtToken) {
        var token = TokensDomain.builder()
                .utilisateur(user)
                .token(jwtToken)
                .expirer(false)
                .revoquer(false)
                .build();
        tokensRepository.save(token);
    }

    private void revokeAllUserToken(UsersDomain user) {
        var validUserToken = tokensRepository.findAllValidTokenByUser(user.getId());
        if (validUserToken.isEmpty())
            return;
        validUserToken.forEach(token -> {
            token.setExpirer(true);
            token.setRevoquer(true);
        });
        tokensRepository.saveAll(validUserToken);
    }
}
