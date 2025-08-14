package com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

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
