package com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom;

public class RejectedProcessException extends RuntimeException{
    private final String errorCode;

    public RejectedProcessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
