package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom;

public class EmailTemplateLoaderException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "EMAIL_TEMPLATE_ERROR";
    private static final String DEFAULT_ERROR_MESSAGE = "Failed to load email templates";

    private final String errorCode;

    public EmailTemplateLoaderException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public EmailTemplateLoaderException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public EmailTemplateLoaderException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
