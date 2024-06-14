package de.stella.agora_web.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import de.stella.agora_web.security.SecurityUser;

@Service
public class TokenService {
    private final JwtEncoder accessTokenEncoder;
    private final JwtEncoder refreshTokenEncoder;

    @Autowired
    public TokenService(
        @Qualifier("jwtAccessTokenEncoder") JwtEncoder accessTokenEncoder,
        @Qualifier("jwtRefreshTokenEncoder") JwtEncoder refreshTokenEncoder
    ) {
        this.accessTokenEncoder = accessTokenEncoder;
        this.refreshTokenEncoder = refreshTokenEncoder;
    }

    public String createAccessToken(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(Long.toString(securityUser.getId()))
                .build();

        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String createRefreshToken(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(Long.toString(securityUser.getId()))
                .build();

        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
