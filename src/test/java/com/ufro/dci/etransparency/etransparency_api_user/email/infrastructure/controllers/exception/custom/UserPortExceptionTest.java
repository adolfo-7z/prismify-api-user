package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserPortExceptionTest {

    @Test
    void shouldUseDefaultConstructor() {
        UserPortException exception = new UserPortException();
        assertEquals("USER_PORT_ERROR", exception.getErrorCode());
        assertEquals("Failed to communicate with user API", exception.getMessage());
    }

    @Test
    void shouldUseMessageConstructor() {
        String message = "User service down";
        UserPortException exception = new UserPortException(message);
        assertEquals("USER_PORT_ERROR", exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldUseCustomCodeAndMessageConstructor() {
        String errorCode = "USER_NOT_FOUND";
        String message = "The user with ID 42 was not found in the databanks.";
        UserPortException exception = new UserPortException(errorCode, message);
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldFallbackToDefaultErrorCodeWhenNullPassed() {
        String message = "Null code provided";
        UserPortException exception = new UserPortException(null, message);
        assertEquals("USER_PORT_ERROR", exception.getErrorCode());
        assertEquals(message, exception.getMessage());
    }

}
