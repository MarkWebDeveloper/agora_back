package de.stella.agora_web.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
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

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

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
    PasswordEncoder passwordEncoder; 
    
    @Autowired
    UserDetailsManager userDetailsManager; 
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
  //JpaUserDetailsService jpaUserDetailsService;

 // public SecurityConfiguration(JpaUserDetailsService jpaUserDetailsService) {
  //  this.jpaUserDetailsService = jpaUserDetailsService;
  //}

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(Customizer.withDefaults())
      .csrf(csrf -> csrf.disable())
      .formLogin(form -> form.disable())
     // .logout(out ->
      //  out.logoutUrl(endpoint + "/logout").deleteCookies("JSESSIONID")
     // )
      .authorizeHttpRequests(auth -> auth
          // Permitir el acceso a todos los posts para usuarios registrados
          .requestMatchers(HttpMethod.GET, endpoint + "/posts/**").permitAll()
          // Permitir el acceso a la creación de posts solo para usuarios
          // registrados
          .requestMatchers(HttpMethod.POST, endpoint + "/posts/**").permitAll()
          // Permitir el acceso a la actualización y eliminación de posts solo
          // para usuarios con rol ADMIN
          .requestMatchers(HttpMethod.PUT, endpoint + "/posts/**").permitAll()
          .requestMatchers(HttpMethod.DELETE, endpoint + "/posts/**").permitAll()
          // Permitir el acceso a todas las respuestas para usuarios registrados
          .requestMatchers(HttpMethod.GET, endpoint + "/replies/**").permitAll()
          // Permitir el acceso a la creación de respuestas solo para usuarios
          // registrados
          .requestMatchers(HttpMethod.POST, endpoint + "/replies").permitAll()
          // Permitir el acceso a la actualización y eliminación de respuestas
          // solo para usuarios con rol ADMIN
          .requestMatchers(HttpMethod.PUT, endpoint + "/replies/**").permitAll()
          .requestMatchers(HttpMethod.DELETE, endpoint + "/replies/**").permitAll()
          // Permitir el acceso a todas las tags para usuarios registrados
          .requestMatchers(HttpMethod.GET, endpoint + "/tags/**").permitAll()
          // Permitir el acceso a la creación de tags solo para usuarios
          // registrados
          .requestMatchers(HttpMethod.POST, endpoint + "/tags").permitAll()
          // Permitir el acceso a la actualización y eliminación de tags solo para
          // usuarios con rol ADMIN
          .requestMatchers(HttpMethod.PUT, endpoint + "/tags/**").permitAll()
          .requestMatchers(HttpMethod.DELETE, endpoint + "/tags/**").permitAll()
          // Permitir el registro de usuarios para todos
          .requestMatchers(HttpMethod.POST, endpoint + "/users/register").permitAll()
          // Permitir el edicion de ususario para todos los roles
          .requestMatchers(HttpMethod.PUT, endpoint + "/users").permitAll()
          .requestMatchers(HttpMethod.GET, endpoint + "/users").permitAll()
          .requestMatchers(HttpMethod.DELETE, endpoint + "/users").permitAll()
          // Permitir el acceso a la autenticación para todos los roles
          .requestMatchers(HttpMethod.GET, endpoint + "/login").permitAll()
          .requestMatchers(HttpMethod.POST, endpoint + "/login").permitAll()
          // Requerir autenticación para todas las demás rutas
          .anyRequest().authenticated()
      )
      .httpBasic(basic -> basic.disable())
                .oauth2ResourceServer((oauth2) -> 
                        oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtToUserConverter)) 
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                .exceptionHandling((exceptions) -> exceptions 
                                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()) 
                                .accessDeniedHandler(new BearerTokenAccessDeniedHandler()) 
                );
        
        http.headers(header -> header.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
    @Bean
    @Primary
    JwtDecoder jwtAccessTokenDecoder() { 
        return NimbusJwtDecoder.withPublicKey(keyUtils.getAccessTokenPublicKey()).build(); 
    } 
  
    @Bean
    @Primary
    JwtEncoder jwtAccessTokenEncoder() { 
        JWK jwk = new RSAKey 
                .Builder(keyUtils.getAccessTokenPublicKey()) 
                .privateKey(keyUtils.getAccessTokenPrivateKey()) 
                .build(); 
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk)); 
        return new NimbusJwtEncoder(jwks); 
    } 
  
    @Bean
    @Qualifier("jwtRefreshTokenDecoder") 
    JwtDecoder jwtRefreshTokenDecoder() { 
        return NimbusJwtDecoder.withPublicKey(keyUtils.getRefreshTokenPublicKey()).build(); 
    } 
  
    @Bean
    @Qualifier("jwtRefreshTokenEncoder") 
    JwtEncoder jwtRefreshTokenEncoder() { 
        JWK jwk = new RSAKey 
                .Builder(keyUtils.getRefreshTokenPublicKey()) 
                .privateKey(keyUtils.getRefreshTokenPrivateKey()) 
                .build(); 
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk)); 
        return new NimbusJwtEncoder(jwks); 
    } 
  
    @Bean
    @Qualifier("jwtRefreshTokenAuthProvider") 
    JwtAuthenticationProvider jwtRefreshTokenAuthProvider() { 
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder()); 
        provider.setJwtAuthenticationConverter(jwtToUserConverter); 
        return provider; 
    } 
  
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() { 
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
        provider.setPasswordEncoder(passwordEncoder); 
        provider.setUserDetailsService(userDetailsManager); 
        return provider; 
    }
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);

    configuration.setAllowedOrigins(
      Arrays.asList(
        "http://localhost:5173",
        "http://localhost:5174",
        "http://localhost:8080"
      )
    );

    configuration.setAllowedMethods(
      Arrays.asList("GET", "POST", "PUT", "DELETE")
    );
    configuration.setAllowedHeaders(
      Arrays.asList("Content-Type", "Authorization")
    );
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(("/**"), configuration);
    return source;
  }


}

