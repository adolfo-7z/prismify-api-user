package com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom;

public class AlreadyExistingProcessException extends RuntimeException {
    
    private final String errorCode;

    public AlreadyExistingProcessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
