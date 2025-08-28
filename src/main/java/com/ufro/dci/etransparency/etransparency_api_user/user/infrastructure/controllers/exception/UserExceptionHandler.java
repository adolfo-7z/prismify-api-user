package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UpdateMapperException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserNotFoundException;

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
        public ResponseEntity<CustomErrorResponse> handleUserNotFound(
                        UserNotFoundException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
        public ResponseEntity<CustomErrorResponse> handleUpdateMapper(
                        UpdateMapperException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
