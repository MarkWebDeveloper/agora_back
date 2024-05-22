package de.stella.agora_web.config.oauth;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    @Value("${access-token.private}")
    private String accessTokenPrivateKey;

    @Value("${refresh-token.private}")
    private String refreshTokenPrivateKey;

    private final long ACCESS_TOKEN_VALIDITY_SECONDS = 3600 * 10; // 10 hours
    private final long REFRESH_TOKEN_VALIDITY_SECONDS = 3600 * 24 * 7; // 7 days

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication.getPrincipal().toString(), ACCESS_TOKEN_VALIDITY_SECONDS, accessTokenPrivateKey);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication.getPrincipal().toString(), REFRESH_TOKEN_VALIDITY_SECONDS, refreshTokenPrivateKey);
    }

    private String generateToken(String subject, long expirationTime, String privateKey) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8)), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }
    
public boolean validateToken(String token, String privateKey) {
    try {
        Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token);
        return true;
    } catch (Exception e) {
        return false;
    }
}


    public String getUsernameFromToken(String token, String privateKey) {
        return getClaimFromToken(token, Claims::getSubject, privateKey);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String privateKey) {
        final Claims claims = getAllClaimsFromToken(token, privateKey);
        return claimsResolver.apply(claims);
    }
  
    private Claims getAllClaimsFromToken(String token, String privateKey) {
        return Jwts.parserBuilder()
               .setSigningKey(Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8)))
               .build()
               .parseClaimsJws(token)
               .getBody();
    }
}
