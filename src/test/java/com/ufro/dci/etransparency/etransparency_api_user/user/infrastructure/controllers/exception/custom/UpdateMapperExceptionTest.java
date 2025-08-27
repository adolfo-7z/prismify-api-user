package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UpdateMapperExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultConstructor() {
        UpdateMapperException exception = new UpdateMapperException();
        assertEquals("Field copy failed", exception.getMessage());
        assertEquals("INTERNAL_SERVER_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        String customMessage = "Mapping failure during the sacred rite of transformation";
        UpdateMapperException exception = new UpdateMapperException(customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals("INTERNAL_SERVER_ERROR", exception.getErrorCode());
    }

    @Test
    void shouldCreateExceptionWithCustomErrorCodeAndMessage() {
        String customMessage = "Machine Spirit rejected the transfer";
        String customCode = "MAPPER_ERROR_001";
        UpdateMapperException exception = new UpdateMapperException(customCode, customMessage);
        assertEquals(customMessage, exception.getMessage());
        assertEquals(customCode, exception.getErrorCode());
    }

    @Test
    void shouldFallbackToDefaultCodeWhenErrorCodeIsNull() {
        UpdateMapperException exception = new UpdateMapperException(null, "Null code test");
        assertEquals("Null code test", exception.getMessage());
        assertEquals("INTERNAL_SERVER_ERROR", exception.getErrorCode());
    }

}
