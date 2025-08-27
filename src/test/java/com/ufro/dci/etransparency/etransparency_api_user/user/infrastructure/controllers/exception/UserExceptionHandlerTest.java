package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UpdateMapperException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserNotFoundException;

class UserExceptionHandlerTest {

    private UserExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UserExceptionHandler();
    }

    @Test
    void shouldHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("USER_ERR_001",
                "User ID 10101 not detected");
        ResponseEntity<CustomErrorResponse> response = handler.handleUserNotFound(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        CustomErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("USER_ERR_001", body.getErrorCode());
        assertEquals("User ID 10101 not detected", body.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), body.getStatus());
    }

    @Test
    void shouldHandleUpdateMapperException() {
        UpdateMapperException exception = new UpdateMapperException("MAPPING_FAILURE",
                "Failed to transfer data");
        ResponseEntity<CustomErrorResponse> response = handler.handleUpdateMapper(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        CustomErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("MAPPING_FAILURE", body.getErrorCode());
        assertEquals("Failed to transfer data", body.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus());
    }

}
