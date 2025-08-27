package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserPortExceptionTest {

    @Test
    void shouldUseDefaultConstructorValues() {
        UserPortException exception = new UserPortException();
        assertEquals("USER_PORT_ERROR", exception.getErrorCode());
        assertEquals("Failed to communicate with user API", exception.getMessage());
    }

    @Test
    void shouldUseCustomMessageWithDefaultErrorCode() {
        UserPortException exception = new UserPortException("Custom failure");
        assertEquals("USER_PORT_ERROR", exception.getErrorCode());
        assertEquals("Custom failure", exception.getMessage());
    }

    @Test
    void shouldUseCustomErrorCodeAndMessage() {
        UserPortException exception = new UserPortException("AUTH_ERR_999", "Authentication service down");
        assertEquals("AUTH_ERR_999", exception.getErrorCode());
        assertEquals("Authentication service down", exception.getMessage());
    }

    @Test
    void shouldFallbackToDefaultErrorCodeWhenNullProvided() {
        UserPortException exception = new UserPortException(null, "Something went wrong");
        assertEquals("USER_PORT_ERROR", exception.getErrorCode());
        assertEquals("Something went wrong", exception.getMessage());
    }

}
