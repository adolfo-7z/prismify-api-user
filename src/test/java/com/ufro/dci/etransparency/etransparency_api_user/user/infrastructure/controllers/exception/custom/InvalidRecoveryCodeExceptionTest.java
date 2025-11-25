package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InvalidRecoveryCodeExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultConstructor() {
        InvalidRecoveryCodeException exception = new InvalidRecoveryCodeException();
        assertEquals("Field copy failed", exception.getMessage());
        assertEquals("BAD_REQUEST", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        String customMessage = "Custom error message";
        InvalidRecoveryCodeException exception = new InvalidRecoveryCodeException(customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals("BAD_REQUEST", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomErrorCodeAndMessage() {
        String customMessage = "Field copy failed";
        String customCode = "ERROR_001";
        InvalidRecoveryCodeException exception = new InvalidRecoveryCodeException(customCode, customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals(customCode, exception.getErrorCode());
    }

    @Test
    void shouldFallbackToDefaultCodeWhenErrorCodeIsNull() {
        InvalidRecoveryCodeException exception = new InvalidRecoveryCodeException(null, "Null code test");
        assertEquals("Null code test", exception.getMessage());
        assertEquals("BAD_REQUEST", exception.getErrorCode());
    }

}
