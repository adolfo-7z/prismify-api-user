package prismify.commons.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.ContentTypeOptionsConfig;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.HstsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

/**
 * Configuración de seguridad para la aplicación.
 * <p>
 * Define las políticas de seguridad, incluyendo CORS, manejo de sesiones,
 * cabeceras de seguridad,
 * rutas públicas y privadas, y la integración del filtro de autenticación
 * personalizado.
 * <ul>
 * <li>Permite el acceso público a los endpoints de login y recuperación de
 * usuarios.</li>
 * <li>Requiere autenticación para el resto de los endpoints.</li>
 * <li>Configura CORS según los orígenes permitidos definidos en las variables
 * de entorno.</li>
 * <li>Deshabilita CSRF y configura la política de sesión como stateless.</li>
 * <li>Agrega el filtro de autenticación JWT/API Key antes del filtro estándar
 * de Spring Security.</li>
 * </ul>
 *
 * @author Adolfo Plaza
 */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

        @Value("${spring.application.cors.allowed-origins}")
        private List<String> allowedOrigins;

        private final AuthenticationFilter authenticationFilter;

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                PathPatternRequestMatcher.Builder apiMatcher = PathPatternRequestMatcher.withDefaults();
                http.csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> cors.configurationSource(websiteConfigurationSource()))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .headers(headers -> headers
                                                .httpStrictTransportSecurity(HstsConfig::disable)
                                                .frameOptions(frame -> frame.sameOrigin())
                                                .contentTypeOptions(ContentTypeOptionsConfig::disable)
                                                .xssProtection(withDefaults()))
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers(apiMatcher.matcher("/auth/login"))
                                                .permitAll()
                                                .requestMatchers(apiMatcher.matcher("/users/recovery/**"))
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        @Bean
        CorsConfigurationSource websiteConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(new ArrayList<>(allowedOrigins));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration
                                .setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                                                "X-API-KEY"));
                configuration.setAllowCredentials(false);
                configuration.setMaxAge(3600L);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

}
