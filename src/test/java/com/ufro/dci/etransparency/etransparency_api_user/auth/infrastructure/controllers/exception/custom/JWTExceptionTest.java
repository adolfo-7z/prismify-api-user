package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JWTExceptionTest {

    @Test
    void shouldUseDefaultConstructorValues() {
        JWTException exception = new JWTException();
        assertEquals("JWT_ERROR", exception.getErrorCode());
        assertEquals("Error with JSON web token", exception.getMessage());
    }

    @Test
    void shouldUseCustomMessageWithDefaultErrorCode() {
        JWTException exception = new JWTException("Custom failure");
        assertEquals("JWT_ERROR", exception.getErrorCode());
        assertEquals("Custom failure", exception.getMessage());
    }

    @Test
    void shouldUseCustomErrorCodeAndMessage() {
        JWTException exception = new JWTException("JWT_ERR_999", "Authentication service down");
        assertEquals("JWT_ERR_999", exception.getErrorCode());
        assertEquals("Authentication service down", exception.getMessage());
    }

    @Test
    void shouldFallbackToDefaultErrorCodeWhenNullProvided() {
        JWTException exception = new JWTException(null, "Error with JSON web token");
        assertEquals("JWT_ERROR", exception.getErrorCode());
        assertEquals("Error with JSON web token", exception.getMessage());
    }
    
}
