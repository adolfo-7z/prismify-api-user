package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.PasswordHasher;

/**
 * Implementación de {@link PasswordHasher} que utiliza el algoritmo
 * BCrypt para encriptar contraseñas.
 * <p>
 * Esta clase se marca como un componente de Spring mediante la anotación
 * {@link Component},
 * por lo que puede ser inyectada en otros beans automáticamente.
 * 
 * @author Adolfo Plaza
 */
@Component
public class BcryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Genera un hash seguro de la contraseña proporcionada usando BCrypt.
     * 
     * @param rawPassword la contraseña en texto plano que se desea encriptar
     * @return un string con el hash de la contraseña
     */
    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

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
