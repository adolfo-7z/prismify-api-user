package com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom;

public class AlreadyRunningProcessException extends RuntimeException {
    private final String errorCode;

    public AlreadyRunningProcessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
