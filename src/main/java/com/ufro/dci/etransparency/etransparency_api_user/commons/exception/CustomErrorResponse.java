package com.ufro.dci.etransparency.etransparency_api_user.commons.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa la estructura de la respuesta de error personalizada.
 * <p>
 * Esta clase se utiliza para enviar información detallada sobre errores a los clientes,
 * incluyendo un código de error, mensaje descriptivo, timestamp y el código de estado HTTP.
 *
 * <ul>
 *   <li>Incluye el código de error asociado al tipo de excepción.</li>
 *   <li>Incluye un mensaje explicativo del error.</li>
 *   <li>Incluye la fecha y hora en que ocurrió el error.</li>
 *   <li>Incluye el código de estado HTTP correspondiente.</li>
 * </ul>
 *
 * @author Adolfo Plaza
 */
@Getter
@Setter
public class CustomErrorResponse {

    private String errorCode;
    private String message;
    private String timestamp;
    private int status;

    public CustomErrorResponse(String errorCode, String message, int status) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
