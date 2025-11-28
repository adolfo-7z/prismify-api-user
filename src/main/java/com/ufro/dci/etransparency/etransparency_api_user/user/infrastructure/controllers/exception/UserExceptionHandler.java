package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.responses.ApiError;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.responses.ApiResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.*;

/**
 * Clase encargada de manejar las excepciones personalizadas relacionadas con
 * los usuarios
 * en la aplicación Spring Boot.
 * <p>
 * Esta clase intercepta excepciones lanzadas en los controladores y devuelve
 * una respuesta
 * estructurada con el código de error, mensaje y estado HTTP correspondiente.
 * 
 * @author Adolfo Plaza
 */
@RestControllerAdvice
public class UserExceptionHandler {

        private String traceId() {
                String id = MDC.get("traceId");
                return id != null ? id : "";
        }

        /**
         * Maneja la excepción {@link UserNotFoundException} lanzada cuando
         * un usuario no es encontrado en el sistema.
         * 
         * @param exception la excepción que contiene información sobre el error
         * @return un {@link ResponseEntity} que contiene un {@link CustomErrorResponse}
         *         con
         *         el código de error, mensaje y el estado HTTP 404 (Not Found)
         */
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ApiResponse<Object>> handleUserNotFound(
                        UserNotFoundException exception) {
                ApiError error = new ApiError(
                                exception.getErrorCode(),
                                exception.getMessage(),
                                traceId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(error));
        }

        /**
         * Maneja la excepción {@link UpdateMapperException} lanzada cuando
         * ocurre un error durante la actualización de datos mediante el mapper.
         * 
         * @param exception la excepción que contiene información sobre el error
         * @return un {@link ResponseEntity} que contiene un {@link CustomErrorResponse}
         *         con
         *         el código de error, mensaje y el estado HTTP 500 (Internal Server
         *         Error)
         */
        @ExceptionHandler(UpdateMapperException.class)
        public ResponseEntity<ApiResponse<Object>> handleUpdateMapper(
                        UpdateMapperException exception) {
                ApiError error = new ApiError(
                                exception.getErrorCode(),
                                exception.getMessage(),
                                traceId());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(error));
        }

        /**
         * Maneja la excepción {@link InvalidRecoveryCodeException} lanzada cuando
         * ocurre un error durante el ingreso de un código de recuperación de
         * contraseña.
         * 
         * @param exception la excepción que contiene información sobre el error
         * @return un {@link ResponseEntity} que contiene un {@link CustomErrorResponse}
         *         con
         *         el código de error, mensaje y el estado HTTP 400 (Bad Request)
         */
        @ExceptionHandler(InvalidRecoveryCodeException.class)
        public ResponseEntity<ApiResponse<Object>> handleInvalidRecoveryCode(
                        InvalidRecoveryCodeException exception) {
                ApiError error = new ApiError(
                                exception.getErrorCode(),
                                exception.getMessage(),
                                traceId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(error));
        }

        /**
         * Maneja la excepción {@link InvalidPasswordException} lanzada cuando
         * ocurre un error durante el ingreso de una nueva contraseña.
         * 
         * @param exception la excepción que contiene información sobre el error
         * @return un {@link ResponseEntity} que contiene un {@link CustomErrorResponse}
         *         con
         *         el código de error, mensaje y el estado HTTP 400 (Bad Request)
         */
        @ExceptionHandler(InvalidPasswordException.class)
        public ResponseEntity<ApiResponse<Object>> handleInvalidPassword(
                        InvalidPasswordException exception) {
                ApiError error = new ApiError(
                                exception.getErrorCode(),
                                exception.getMessage(),
                                traceId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(error));
        }

        /**
         * Maneja la excepción {@link UserEmailPortException} lanzada cuando
         * no se logra establecer conección con la API de email.
         * 
         * @param exception la excepción que contiene información sobre el error
         * @return un {@link ResponseEntity} que contiene un {@link CustomErrorResponse}
         *         con
         *         el código de error, mensaje y el estado HTTP 500 (Internal Server
         *         Error)
         */
        @ExceptionHandler(UserEmailPortException.class)
        public ResponseEntity<ApiResponse<Object>> handleUserEmailPort(
                        UserEmailPortException exception) {
                ApiError error = new ApiError(
                                exception.getErrorCode(),
                                exception.getMessage(),
                                traceId());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(error));
        }

}
