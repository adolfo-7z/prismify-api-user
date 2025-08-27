package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom;

public class UserNotFoundException extends RuntimeException{

    private static final String DEFAULT_ERROR_CODE = "USER_NOT_FOUND";
    private static final String DEFAULT_ERROR_MESSAGE = "User with the present ID was not found";

    private final String errorCode;

    public UserNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UserNotFoundException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public UserNotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }
    
}
