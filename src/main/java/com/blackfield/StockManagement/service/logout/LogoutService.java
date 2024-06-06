package com.blackfield.StockManagement.service.logout;

import com.blackfield.StockManagement.repository.TokensRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokensRepository tokensRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokensRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpirer(true);
            storedToken.setRevoquer(true);
            tokensRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}

