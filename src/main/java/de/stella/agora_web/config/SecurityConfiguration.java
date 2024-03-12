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

        // Configura CORS (Cross-Origin Resource Sharing) con valores predeterminados.
        http
                .cors(Customizer.withDefaults())
                // Deshabilita la protección CSRF (Cross-Site Request Forgery).
                .csrf(csrf -> csrf.disable())
                // Deshabilita el inicio de sesión basado en formularios.
                .formLogin(form -> form.disable())
                // Configura la URL de cierre de sesión y elimina la cookie JSESSIONID.
                .logout(out -> out
                        .logoutUrl(endpoint + "/logout")
                        .deleteCookies("JSESSIONID"))
                // Configura las reglas de autorización para las solicitudes HTTP.
                .authorizeHttpRequests(auth -> auth
                // Permite el acceso a las rutas de textos e imágenes para todos.
                //textos e imagenes paginas estaticas debens ser accesibles por todos pero solo editables por el admin
                        .requestMatchers(HttpMethod.GET, endpoint + "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/v1/texts").permitAll()
                        // Solo permite a los usuarios con rol ADMIN crear textos e imágenes.
                        .requestMatchers(HttpMethod.POST, endpoint + "/texts", endpoint+"/images").hasRole("ADMIN")
                        // Solo permite a los usuarios con rol ADMIN eliminar textos.
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/texts/**").hasRole("ADMIN")
                        // Solo permite a los usuarios con rol ADMIN actualizar textos.
                        .requestMatchers(HttpMethod.PUT, endpoint + "/texts/**").hasRole("ADMIN")
                        // Permite el acceso a la vista de registro para todos.
                //vista regitro accesible por todos y login por user y admin
                        .requestMatchers(HttpMethod.POST, endpoint + "/users/register").permitAll()
                        // Solo permite a los usuarios con rol ADMIN o USER acceder a la vista de inicio de sesión.
                        .requestMatchers(HttpMethod.GET, endpoint + "/login").hasAnyRole("ADMIN","USER")
                        // Configura las reglas de autorización para las suscripciones a posts.
                //manejo de los posts del foro solo usuaios logeados deben poder postear solo el dueño del post o el admin pueden borrarlo , sy solo el duelño editarlo
                       
                        .requestMatchers(HttpMethod.POST, endpoint + "/posts/{id}/subscription").hasAnyRole("ADMIN","USER")
                        // Solo permite a los usuarios con rol ADMIN eliminar suscripciones a posts.
                        .requestMatchers(HttpMethod.DELETE, endpoint + "/posts/{id}/subscription").hasRole("ADMIN")
                        // Solo permite a los usuarios con rol USER actualizar sus suscripciones a posts.
                        .requestMatchers(HttpMethod.PUT, endpoint + "/posts/{id}/subscription").hasRole("USER")
                        // Solo permite a los usuarios con rol ADMIN o USER acceder a los posts.
                        .requestMatchers(HttpMethod.GET, endpoint + "/posts/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, endpoint + "/posts/{id}/reply").hasAnyRole("ADMIN", "USER")
                        // Solo permite a los usuarios con rol ADMIN o USER responder a los posts.
                        .requestMatchers(HttpMethod.POST, endpoint + "/posts/{id}/reply").hasAnyRole("ADMIN", "USER")


                        // Requiere autenticación para cualquier otra solicitud.
                        .anyRequest().permitAll())
                // Configura la autenticación básica HTTP.
                .httpBasic(Customizer.withDefaults())
                // Configura la política de creación de sesiones.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                // Configura las opciones de seguridad de los encabezados HTTP.
                .headers(header -> header.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Crea una nueva configuración CORS.
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite el uso de credenciales en las solicitudes CORS.
        configuration.setAllowCredentials(true);
        // Define los orígenes permitidos para las solicitudes CORS.
        // En este caso, solo se permite el acceso desde "http://localhost:5173".
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        // Define los métodos HTTP permitidos para las solicitudes CORS.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // Define los encabezados permitidos en las solicitudes CORS.
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        // Crea una nueva fuente de configuración CORS basada en URL.
    
        // Configuración CORS específica para la ruta /texts
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Registra la configuración CORS específica para la ruta "/texts".
        // Esto significa que la configuración CORS definida anteriormente
        // se aplicará solo a las solicitudes que accedan a la ruta "/texts".
        source.registerCorsConfiguration("/texts", configuration);
    
        // Devuelve la fuente de configuración CORS.
        return source;
    }
    



    @Bean
    PasswordEncoder passwordEncoder() {
        // Devuelve un codificador de contraseñas BCrypt.
        // BCrypt es un algoritmo de hash de contraseñas que es seguro y eficiente.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Crea un detalle de usuario para el administrador.
        // El constructor del usuario asegura que la contraseña se codifique antes de guardarla en memoria.

        // The builder will ensure the passwords are encoded before saving in memory
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO")
                .roles("ADMIN")
                .build();

        // Crea un detalle de usuario para el usuario regular.
        UserDetails user = User.builder()
            .username("user")
            .password("$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO")
            .roles("USER")
            .build();

        // Crea una colección para almacenar los detalles de los usuarios.
        Collection<UserDetails> users = new ArrayList<>();

        // Agrega los detalles de los usuarios a la colección.
        users.add(admin);
        users.add(user);

        // Devuelve un gestor de detalles de usuario en memoria con los usuarios definidos.
        // Este gestor se utilizará para la autenticación y autorización en la aplicación.
        return new InMemoryUserDetailsManager(users);
    }

}
