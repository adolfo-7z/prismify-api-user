package com.ufro.dci.etransparency.etransparency_api_user.auth.application.usecases;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model.AuthUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.in.LoginUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.*;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del caso de uso de login de un usuario.
 * <p>
 * Esta clase se encarga de autenticar a un usuario verificando su nombre de
 * usuario y contraseña,
 * y generando un token en caso de que las credenciales sean válidas.
 * 
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final LoadAuthUserPort loadAuthUserPort;
    private final PasswordMatcher hasher;
    private final TokenProvider tokenProvider;

    /**
     * Intenta autenticar a un usuario con un nombre de usuario y contraseña
     * proporcionados.
     *
     * @param username    El nombre de usuario del usuario que intenta iniciar
     *                    sesión.
     * @param rawPassword La contraseña sin cifrar proporcionada por el usuario.
     * @return Un token de autenticación si las credenciales son válidas.
     * @throws InvalidCredentialsException Si el usuario no existe, está inactivo o
     *                                     la contraseña es incorrecta.
     */
    @Override
    public String login(String username, String rawPassword) throws InvalidCredentialsException {
        AuthUserDetails user = loadAuthUserPort.loadByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);
        if (!user.isActive() || !hasher.matches(rawPassword, user.hashedPassword())) {
            throw new InvalidCredentialsException();
        }
        return tokenProvider.generateToken(user);
    }
}
