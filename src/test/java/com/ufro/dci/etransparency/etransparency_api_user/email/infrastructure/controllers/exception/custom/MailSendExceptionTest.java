package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MailSendExceptionTest {

    @Test
    void shouldUseDefaultConstructor() {
        MailSendException exception = new MailSendException();
        assertEquals("MAIL_SEND_ERROR", exception.getErrorCode());
        assertEquals("Failed to send email", exception.getMessage());
    }

    @Test
    void shouldUseMessageConstructor() {
        String message = "User service down";
        MailSendException exception = new MailSendException(message);
        assertEquals("MAIL_SEND_ERROR", exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldUseCustomCodeAndMessageConstructor() {
        String errorCode = "CUSTOM_ERROR";
        String message = "Failed to send email to recipient";
        MailSendException exception = new MailSendException(errorCode, message);
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldFallbackToDefaultErrorCodeWhenNullPassed() {
        String message = "Null code provided";
        MailSendException exception = new MailSendException(null, message);
        assertEquals("MAIL_SEND_ERROR", exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }
    
}
