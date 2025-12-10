package prismify.auth.application.services;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import lombok.RequiredArgsConstructor;
import prismify.auth.domain.ports.in.LoginUseCase;
import prismify.auth.domain.ports.in.ValidateTokenUseCase;

/**
 * Servicio de autenticación que implementa los casos de uso de login y
 * validación de tokens.
 * <p>
 * Esta clase delega la funcionalidad real de login y validación de tokens a las
 * implementaciones
 * proporcionadas de {@link LoginUseCase} y {@link ValidateTokenUseCase}.
 * 
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class AuthService implements LoginUseCase, ValidateTokenUseCase {

    private final LoginUseCase loginUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;

    /**
     * Realiza el proceso de login para un usuario con nombre de usuario y
     * contraseña.
     * <p>
     * Este método delega la lógica de autenticación al {@link LoginUseCase}
     * proporcionado.
     *
     * @param username    Nombre de usuario del usuario que intenta iniciar sesión.
     * @param rawPassword Contraseña en texto plano del usuario.
     * @return Un token de autenticación si las credenciales son válidas.
     * @throws InvalidCredentialsException Si el usuario o la contraseña son
     *                                     incorrectos.
     */
    @Override
    public String login(String username, String rawPassword) throws InvalidCredentialsException {
        return loginUseCase.login(username, rawPassword);
    }

    /**
     * Valida un token de autenticación.
     * <p>
     * Este método delega la verificación del token al {@link ValidateTokenUseCase}
     * proporcionado.
     *
     * @param token Token de autenticación a validar.
     * @return {@code true} si el token es válido, {@code false} en caso contrario.
     */
    @Override
    public boolean validateToken(String token) {
        return validateTokenUseCase.validateToken(token);
    }
}
