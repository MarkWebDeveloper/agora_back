package de.stella.agora_web.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${api-endpoint}")
    String endpoint;

   
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .logout(out -> out
                        .logoutUrl(endpoint + "/logout")
                        .deleteCookies("JSESSIONID"))
                .authorizeHttpRequests(auth -> auth
                        // Permitir el acceso a todos los posts para usuarios registrados
                        .requestMatchers(HttpMethod.GET, endpoint + "/posts/**").authenticated()
                        // Permitir el acceso a la creación de posts solo para usuarios registrados
                        .requestMatchers(HttpMethod.POST, endpoint + "/posts").authenticated()
                        // Permitir el acceso a la actualización y eliminación de posts solo para usuarios con rol ADMIN
                        .requestMatchers(HttpMethod.PUT, endpoint + "/posts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/posts/**").hasRole("ADMIN")
                        // Permitir el acceso a todas las respuestas para usuarios registrados
                        .requestMatchers(HttpMethod.GET, endpoint + "/replies/**").authenticated()
                        // Permitir el acceso a la creación de respuestas solo para usuarios registrados
                        .requestMatchers(HttpMethod.POST, endpoint + "/replies").authenticated()
                        // Permitir el acceso a la actualización y eliminación de respuestas solo para usuarios con rol ADMIN
                        .requestMatchers(HttpMethod.PUT, endpoint + "/replies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/replies/**").hasRole("ADMIN")
                        // Permitir el acceso a todas las tags para usuarios registrados
                        .requestMatchers(HttpMethod.GET, endpoint + "/tags/**").authenticated()
                        // Permitir el acceso a la creación de tags solo para usuarios registrados
                        .requestMatchers(HttpMethod.POST, endpoint + "/tags").authenticated()
                        // Permitir el acceso a la actualización y eliminación de tags solo para usuarios con rol ADMIN
                        .requestMatchers(HttpMethod.PUT, endpoint + "/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/tags/**").hasRole("ADMIN")
                        // Permitir el registro de usuarios para todos
                        .requestMatchers(HttpMethod.POST, endpoint + "/users/register").permitAll()
                        // Permitir el acceso a la autenticación para todos los roles
                        .requestMatchers(HttpMethod.GET, endpoint + "/login").hasAnyRole("ADMIN", "USER")
                        // Requerir autenticación para todas las demás rutas
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));


        http.headers(header -> header.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        // The builder will ensure the passwords are encoded before saving in memory
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO")
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
            .username("user")
            .password("$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO")
            .roles("USER")
            .build();

        Collection<UserDetails> users = new ArrayList<>();

        users.add(admin);
        users.add(user);

        return new InMemoryUserDetailsManager(users);
    }

}