package de.stella.agora_web.auth;

import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;


@Configuration
public class JwtConfig {

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        // Load the public key from a file
        RSAPublicKey publicKey = KeyUtils.loadPublicKey("path/to/pubkey.pem");

        // Use NimbusJwtEncoder with the loaded public key
        return new NimbusJwtEncoder((JWKSource<SecurityContext>) publicKey);
    }
}