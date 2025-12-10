package prismify.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Clase que maneja excepciones globales en los controladores de la aplicación.
 * <p>
 * Esta clase está anotada con {@link RestControllerAdvice} para capturar
 * excepciones
 * lanzadas por los controladores y devolver respuestas personalizadas.
 * 
 * @author Adolfo Plaza
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de validación lanzadas cuando los argumentos de un
     * método
     * anotado con {@code @Valid} no cumplen con las restricciones definidas.
     * <p>
     * Este método captura la excepción {@link MethodArgumentNotValidException},
     * construye un mensaje detallado de los errores de validación y devuelve un
     * {@link ResponseEntity} con un objeto {@link CustomErrorResponse} y estado
     * HTTP 400 (BAD_REQUEST).
     *
     * @param exception la excepción de validación capturada
     * @return un {@link ResponseEntity} que contiene un {@link CustomErrorResponse}
     *         con detalles de los errores
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage())
                    .append(". ");
        }
        CustomErrorResponse response = new CustomErrorResponse("BAD_REQUEST", errorMessage.toString(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
