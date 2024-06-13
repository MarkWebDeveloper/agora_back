package de.stella.agora_web.auth;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtConfig {

    @Value("${refresh-token.public-key-path}")
    private String refreshTokenPublicKeyPath;

    @Value("${access-token.public-key-path}")
    private String accessTokenPublicKeyPath;

    @Bean
    @Qualifier("accessTokenEncoder")
    public JwtEncoder accessTokenEncoder() {
        return new NimbusJwtEncoder(new AccessTokenKeyProvider(accessTokenPublicKeyPath));
    }

    @Bean
    @Qualifier("refreshTokenEncoder")
    public JwtEncoder refreshTokenEncoder() {
        return new NimbusJwtEncoder(new AccessTokenKeyProvider(refreshTokenPublicKeyPath));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(accessTokenPublicKeyPath).build();
    }

    private static class AccessTokenKeyProvider implements JWKSource<SecurityContext> {

        private final String publicKeyPath;

        public AccessTokenKeyProvider(String publicKeyPath) {
            this.publicKeyPath = publicKeyPath;
        }

        @Override
        public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) {
            try (InputStream inputStream = new FileInputStream(publicKeyPath)) {
                return JWKSet.load(inputStream).getKeys();
            } catch (IOException | ParseException e) {
                throw new RuntimeException("Failed to load JWKSet from " + publicKeyPath, e);
            }
        }
    }
}

