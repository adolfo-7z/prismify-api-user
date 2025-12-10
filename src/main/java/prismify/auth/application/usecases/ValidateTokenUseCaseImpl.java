package prismify.auth.application.usecases;

import prismify.auth.domain.ports.in.ValidateTokenUseCase;
import prismify.auth.domain.ports.out.TokenProvider;

/**
 * Implementación de {@link ValidateTokenUseCase} que valida tokens utilizando
 * un {@link TokenProvider}.
 * 
 * <p>
 * Esta clase se encarga de delegar la validación de un token a la capa de
 * {@link TokenProvider}.
 * 
 * @author Adolfo Plaza
 */
public class ValidateTokenUseCaseImpl implements ValidateTokenUseCase {

    private final TokenProvider tokenProvider;

    /**
     * Crea una nueva instancia de {@code ValidateTokenUseCaseImpl} con el
     * {@link TokenProvider} especificado.
     *
     * @param tokenProvider el proveedor de tokens que se utilizará para validar los
     *                      tokens
     */
    public ValidateTokenUseCaseImpl(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Valida un token delegando la operación al {@link TokenProvider}.
     *
     * @param token el token a validar
     * @return {@code true} si el token es válido; {@code false} en caso contrario
     */
    @Override
    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }

}
