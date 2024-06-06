package com.blackfield.StockManagement.service.login;

import com.blackfield.StockManagement.domain.UsersDomain;
import com.blackfield.StockManagement.exception.InvalidOperationException;
import com.blackfield.StockManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationLoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("loadUserByUsername {}", login);

        return userRepository.findOneWithAuthoritiesByLogin(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("Nom d'utilisateur ou mot de passe incorrect"));
    }


    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, UsersDomain usersDomain) {
        if (!usersDomain.isActiver()) {
            throw new InvalidOperationException("Le compte de l'utilisateur " + login + " n'est pas activé");
        }
        List<GrantedAuthority> grantedAuthorities = usersDomain.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getNom()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(usersDomain.getLogin(),
                usersDomain.getPassword(),
                grantedAuthorities);
    }
}