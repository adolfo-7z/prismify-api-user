package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InvalidPasswordExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultConstructor() {
        InvalidPasswordException exception = new InvalidPasswordException();
        assertEquals("Invalid credentials", exception.getMessage());
        assertEquals("BAD_REQUEST", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        String customMessage = "Invalid password";
        InvalidPasswordException exception = new InvalidPasswordException(customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals("BAD_REQUEST", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomErrorCodeAndMessage() {
        String customMessage = "Invalid password";
        String customCode = "ERROR_001";
        InvalidPasswordException exception = new InvalidPasswordException(customCode, customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals(customCode, exception.getErrorCode());
    }

    @Test
    void shouldFallbackToDefaultCodeWhenErrorCodeIsNull() {
        InvalidPasswordException exception = new InvalidPasswordException(null, "Null code test");
        assertEquals("Null code test", exception.getMessage());
        assertEquals("BAD_REQUEST", exception.getErrorCode());
    }
    
}
