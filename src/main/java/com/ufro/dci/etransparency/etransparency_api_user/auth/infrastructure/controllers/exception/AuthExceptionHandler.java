package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.JWTException;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.UserPortException;
import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;

/**
 * Manejador global de excepciones para el módulo de autenticación.
 * <p>
 * Esta clase captura y gestiona las excepciones personalizadas relacionadas con la autenticación,
 * devolviendo respuestas de error estructuradas al cliente.
 * <ul>
 *   <li>Maneja excepciones de tipo {@link UserPortException} y {@link JWTException}.</li>
 *   <li>Devuelve una respuesta con el código de error, mensaje descriptivo y estado HTTP 500.</li>
 * </ul>
 *
 * @author Adolfo Plaza
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * Maneja excepciones de tipo {@link UserPortException} lanzadas durante el proceso de autenticación.
     * <p>
     * Devuelve una respuesta de error personalizada con el código y mensaje de la excepción,
     * junto con el estado HTTP 500 (Internal Server Error).
     *
     * @param exception la excepción capturada de tipo UserPortException
     * @return ResponseEntity con la estructura de error personalizada y estado 500
     */
    @ExceptionHandler(UserPortException.class)
    public ResponseEntity<CustomErrorResponse> handleUserPort(
            UserPortException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja excepciones de tipo {@link JWTException} relacionadas con errores en el manejo de tokens JWT.
     * <p>
     * Devuelve una respuesta de error personalizada con el código y mensaje de la excepción,
     * junto con el estado HTTP 500 (Internal Server Error).
     *
     * @param exception la excepción capturada de tipo JWTException
     * @return ResponseEntity con la estructura de error personalizada y estado 500
     */
    @ExceptionHandler(JWTException.class)
    public ResponseEntity<CustomErrorResponse> handleJWT(
            JWTException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
