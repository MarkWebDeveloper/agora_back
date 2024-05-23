package de.stella.agora_web.config;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import de.stella.agora_web.config.oauth.AuthController;
import de.stella.agora_web.config.oauth.JWTtoUserConverter;
import de.stella.agora_web.config.oauth.KeyUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${api-endpoint}")
    String endpoint;

    @Autowired
    JWTtoUserConverter jwtToUserConverter;

    @Autowired
    KeyUtils keyUtils;

    @Autowired
    UserDetailsManager userDetailsManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("unused")
    private JwtEncoder createJwtEncoder(JWK key) {
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(key)));
    }

    private JwtDecoder createJwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtDecoder jwtAccessTokenDecoder() {
        return createJwtDecoder(keyUtils.getAccessTokenPublicKey());
    }

    @Bean
    public JwtEncoder jwtAccessTokenEncoder() throws JOSEException {
        return createJwtEncoder(keyUtils.getAccessTokenKeyPair());
    }

@SuppressWarnings("unchecked")
private JwtEncoder createJwtEncoder(KeyPair accessTokenKeyPair) throws JOSEException {
    RSAKey rsaKey = new RSAKeyGenerator(2048).generate();
    JWK jwk = rsaKey.toPublicJWK();

    JWKSource<SecurityContext> jwks = new JWKSource<SecurityContext>() {
        @Override
        public List<JWK> get(JWKSelector selector, SecurityContext context) {
            return (List<JWK>) jwk;
        }
    };

    JwtClaimsSet claimsSet = JwtClaimsSet.builder()
        .issuer("self")
        .subject("test")
        
        .claim("preferred_username", "test")
        .claim("email", "test")
        .claim("scope", "openid profile email")
        .claim("name", "test")
        .claim("nonce", "test")
        .claim("aud", endpoint)
        .claim("exp", 3600)
        .claim("iat", Instant.now().getEpochSecond())
        .claim("jti", "test")
        .claim("auth_time", 3600)
        .claim("azp", "test")
        .claim("nonce", "test")
        .claim("at_hash", "test")

        .build();

    JwtEncoderParameters parameters = JwtEncoderParameters.from(claimsSet);
    return new NimbusJwtEncoder(jwks);
}

    @Bean
    public JwtDecoder jwtRefreshTokenDecoder() {
        return createJwtDecoder(keyUtils.getRefreshTokenPublicKey());
    }

    @Bean
    public JwtEncoder jwtRefreshTokenEncoder() throws JOSEException {
        return createJwtEncoder(keyUtils.getRefreshTokenKeyPair());
    }

    @Bean
    public JwtAuthenticationProvider jwtRefreshTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder());
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsManager);
        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173",
            "http://localhost:5174",
            "http://localhost:8080"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthController authController(UserDetailsManager userDetailsManager) {
        return new AuthController(userDetailsManager);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsManager).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, endpoint + "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, endpoint + "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, endpoint + "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, endpoint + "/replies/**").permitAll()
                        .requestMatchers(HttpMethod.POST, endpoint + "/replies").permitAll()
                        .requestMatchers(HttpMethod.PUT, endpoint + "/replies/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/replies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, endpoint + "/tags/**").permitAll()
                        .requestMatchers(HttpMethod.POST, endpoint + "/tags").permitAll()
                        .requestMatchers(HttpMethod.PUT, endpoint + "/tags/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/tags/**").permitAll()
                        .requestMatchers(HttpMethod.POST, endpoint + "/users/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, endpoint + "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, endpoint + "/users").permitAll()
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, endpoint + "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, endpoint + "/login").permitAll()
                        .anyRequest().authenticated()
                ).httpBasic(basic -> basic.disable())
                .oauth2ResourceServer((oauth2) -> 
                        oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtToUserConverter)) 
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                .exceptionHandling((exceptions) -> exceptions 
                                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()) 
                                .accessDeniedHandler(new BearerTokenAccessDeniedHandler()) 
                )
                .headers((headers) -> headers.frameOptions(frame -> frame.sameOrigin()))
                .build();
    }
}
