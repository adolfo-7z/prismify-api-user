package prismify.email.infrastructure.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import prismify.commons.exception.CustomErrorResponse;
import prismify.email.infrastructure.controllers.exception.custom.EmailTemplateLoaderException;
import prismify.email.infrastructure.controllers.exception.custom.MailSendException;
import prismify.email.infrastructure.controllers.exception.custom.UserPortException;

/**
 * Clase que maneja excepciones relacionadas con operaciones de correo
 * dentro de la aplicación. Esta clase captura excepciones de tipo
 * {@link UserPortException} y devuelve una respuesta personalizada al cliente.
 * 
 * @author Adolfo Plaza
 */
@RestControllerAdvice
public class MailExceptionHandler {

        /**
         * Maneja las excepciones de tipo {@link UserPortException}.
         * 
         * Este método captura la excepción lanzada, construye un objeto
         * {@link CustomErrorResponse} con el código de error, mensaje y código HTTP,
         * y devuelve una {@link ResponseEntity} con estado 500 (Internal Server Error).
         * 
         * @param exception la excepción de tipo {@link UserPortException} lanzada
         *                  durante
         *                  la ejecución de una operación relacionada con el correo.
         * @return una {@link ResponseEntity} que contiene el
         *         {@link CustomErrorResponse}
         *         con información del error y un estado HTTP 500.
         */
        @ExceptionHandler(UserPortException.class)
        public ResponseEntity<CustomErrorResponse> handleUserPort(
                        UserPortException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /**
         * Maneja las excepciones de tipo {@link MailSendException}.
         * 
         * Este método captura la excepción lanzada, construye un objeto
         * {@link CustomErrorResponse} con el código de error, mensaje y código HTTP,
         * y devuelve una {@link ResponseEntity} con estado 502 (Bad Gateway).
         * 
         * @param exception la excepción de tipo {@link MailSendException} lanzada
         *                  durante
         *                  la ejecución de una operación relacionada con el correo.
         * @return una {@link ResponseEntity} que contiene el
         *         {@link CustomErrorResponse}
         *         con información del error y un estado HTTP 502.
         */
        @ExceptionHandler(MailSendException.class)
        public ResponseEntity<CustomErrorResponse> handleMailSend(
                        MailSendException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.BAD_GATEWAY.value());
                return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }

        /**
         * Maneja las excepciones de tipo {@link EmailTemplateLoaderException}.
         * 
         * Este método captura la excepción lanzada, construye un objeto
         * {@link CustomErrorResponse} con el código de error, mensaje y código HTTP,
         * y devuelve una {@link ResponseEntity} con estado 500 (Internal Server Error).
         * 
         * @param exception la excepción de tipo {@link EmailTemplateLoaderException} lanzada
         *                  durante
         *                  la ejecución de una operación relacionada con el Email Template Loader.
         * @return una {@link ResponseEntity} que contiene el
         *         {@link CustomErrorResponse}
         *         con información del error y un estado HTTP 502.
         */
        @ExceptionHandler(EmailTemplateLoaderException.class)
        public ResponseEntity<CustomErrorResponse> handleEmailTemplateLoader(
                        EmailTemplateLoaderException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
