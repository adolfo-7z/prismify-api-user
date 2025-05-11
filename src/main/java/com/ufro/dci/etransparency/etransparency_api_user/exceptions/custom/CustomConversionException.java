package com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom;

public class CustomConversionException extends RuntimeException {

    private final String errorCode;

    public CustomConversionException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
