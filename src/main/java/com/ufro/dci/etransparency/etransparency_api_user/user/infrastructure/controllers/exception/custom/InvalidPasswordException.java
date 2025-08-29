package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom;

public class InvalidPasswordException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "BAD_REQUEST";
    private static final String DEFAULT_ERROR_MESSAGE = "Invalid received passwords";

    private final String errorCode;

    public InvalidPasswordException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public InvalidPasswordException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public InvalidPasswordException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
