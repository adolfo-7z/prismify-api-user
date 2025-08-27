package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom;

public class UpdateMapperException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "INTERNAL_SERVER_ERROR";
    private static final String DEFAULT_ERROR_MESSAGE = "Field copy failed";

    private final String errorCode;

    public UpdateMapperException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UpdateMapperException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UpdateMapperException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
