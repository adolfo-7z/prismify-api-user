package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom;

public class MailSendException extends RuntimeException {

    private static final String DEFAULT_ERROR_CODE = "MAIL_SEND_ERROR";
    private static final String DEFAULT_ERROR_MESSAGE = "Failed to send email";

    private final String errorCode;

    public MailSendException() {
        super(DEFAULT_ERROR_MESSAGE);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public MailSendException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public MailSendException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode != null ? errorCode : DEFAULT_ERROR_CODE;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
