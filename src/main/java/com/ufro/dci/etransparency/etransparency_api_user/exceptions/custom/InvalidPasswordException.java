package com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom;

public class InvalidPasswordException extends RuntimeException {
    private final String errorCode;

    public InvalidPasswordException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
