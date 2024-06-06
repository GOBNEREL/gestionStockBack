package com.blackfield.StockManagement.jwt;

import com.blackfield.StockManagement.domain.UsersDomain;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String secretKey = "404E635266556A586E3272357538782F413F4";

    String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UsersDomain usersDomain) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, usersDomain);
    }

    private String createToken(Map<String, Object> claims, UsersDomain usersDomain) {

        return Jwts.builder().setClaims(claims)
                .setSubject(usersDomain.getLogin())
//                .claim("idStructure", utilisateurDomain.getStructure().getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMicros(30)))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    Boolean validateToken(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
