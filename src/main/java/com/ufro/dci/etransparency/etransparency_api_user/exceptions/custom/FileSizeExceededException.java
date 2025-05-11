package com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom;

public class FileSizeExceededException extends RuntimeException{

    private final String errorCode;

    public FileSizeExceededException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
    
}
