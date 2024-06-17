package de.stella.agora_web.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import de.stella.agora_web.user.services.JpaUserDetailsService;

@Import(JwtSecurityConfig.class)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Value("${api-endpoint}")
  String endpoint;

  JpaUserDetailsService jpaUserDetailsService;

    @SuppressWarnings("unused")
    private JwtSecurityConfig jwtSecurityConfig;

  public SecurityConfiguration(JpaUserDetailsService jpaUserDetailsService) {
    this.jpaUserDetailsService = jpaUserDetailsService;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(Customizer.withDefaults())
      .csrf(csrf -> csrf.disable()) 
      .formLogin(form -> form.disable())
      .logout(out ->
        out.logoutUrl(endpoint + "/logout").deleteCookies("JSESSIONID")
      )
      .authorizeHttpRequests(auth -> auth
      .requestMatchers(PathRequest.toH2Console()).permitAll()
      .requestMatchers("/error").permitAll()
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
          .anyRequest().permitAll()
      )
      .httpBasic(Customizer.withDefaults())
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
      );

    http.headers(header -> header.frameOptions(frame -> frame.sameOrigin()));
    return http.build();
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

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

