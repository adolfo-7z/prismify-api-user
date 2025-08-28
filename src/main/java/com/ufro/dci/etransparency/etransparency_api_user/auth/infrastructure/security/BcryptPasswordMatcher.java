package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.PasswordMatcher;

/**
 * Implementación de {@link PasswordMatcher} que utiliza el algoritmo
 * BCrypt para verificar si una contraseña en texto plano coincide
 * con una contraseña cifrada.
 * 
 * @author Adolfo Plaza
 */
@Component
public class BcryptPasswordMatcher implements PasswordMatcher {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Verifica si la contraseña en texto plano coincide con la contraseña cifrada.
     *
     * @param rawPassword    la contraseña en texto plano proporcionada por el
     *                       usuario
     * @param hashedPassword la contraseña cifrada almacenada (por ejemplo, en base
     *                       de datos)
     * @return {@code true} si las contraseñas coinciden, {@code false} en caso
     *         contrario
     */
    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

}
