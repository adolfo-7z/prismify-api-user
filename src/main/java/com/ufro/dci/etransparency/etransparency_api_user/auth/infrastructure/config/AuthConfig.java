package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ufro.dci.etransparency.etransparency_api_user.auth.application.services.AuthService;
import com.ufro.dci.etransparency.etransparency_api_user.auth.application.usecases.*;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.*;

/**
 * Clase de configuración para la autenticación de usuarios.
 * <p>
 * Esta clase define los beans necesarios para el servicio de autenticación,
 * incluyendo la creación de {@link AuthService} que encapsula los casos de uso
 * de login y validación de tokens.
 * 
 * @author Adolfo Plaza
 */
@Configuration
public class AuthConfig {

    /**
     * Crea un bean de {@link AuthService} configurado con los casos de uso
     * de login y validación de tokens.
     * <p>
     * Este método recibe las dependencias necesarias para la autenticación:
     * <ul>
     * <li>{@link LoadAuthUserPort}: puerto de carga de usuarios para login.</li>
     * <li>{@link PasswordMatcher}: utilidad para verificar contraseñas.</li>
     * <li>{@link TokenProvider}: proveedor de tokens para la generación y
     * validación de JWT.</li>
     * </ul>
     * 
     * @param loadAuthUserPort el puerto para cargar usuarios de autenticación
     * @param matcher          el verificador de contraseñas
     * @param tokenProvider    el proveedor de tokens
     * @return una instancia de {@link AuthService} configurada
     */
    @Bean
    AuthService authService(LoadAuthUserPort loadAuthUserPort,
            PasswordMatcher matcher,
            TokenProvider tokenProvider) {
        return new AuthService(new LoginUseCaseImpl(loadAuthUserPort, matcher, tokenProvider),
                new ValidateTokenUseCaseImpl(tokenProvider));
    }

}
