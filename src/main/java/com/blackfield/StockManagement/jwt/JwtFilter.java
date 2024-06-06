package com.blackfield.StockManagement.jwt;

import com.blackfield.StockManagement.repository.TokensRepository;
import com.blackfield.StockManagement.service.login.ApplicationLoginService;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokensRepository tokensRepository;
    private final ApplicationLoginService applicationLoginService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");


        // Vérification si le authHeader est null
        if (Strings.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = jwtUtil.extractUsername(jwt);


        if(StringUtils.hasLength(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Récupération de l'utilisateur
            UserDetails userDetails = applicationLoginService.loadUserByUsername(username);

            boolean isTokenValid = tokensRepository.findByToken(jwt)
                    .map(t -> !t.isExpirer() && !t.isRevoquer())
                    .orElse(false);

            // Vérification que le token appartient à l'utilisateur connecté
            if(Boolean.TRUE.equals(jwtUtil.validateToken(jwt, userDetails)) && isTokenValid) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
