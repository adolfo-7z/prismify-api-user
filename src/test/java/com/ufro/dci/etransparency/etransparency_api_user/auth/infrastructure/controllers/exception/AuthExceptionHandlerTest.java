package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.JWTException;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.UserPortException;
import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;

class AuthExceptionHandlerTest {

    private AuthExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AuthExceptionHandler();
    }

    @Test
    void shouldHandleUserPortException() {
        UserPortException exception = new UserPortException("AUTH_ERR_001", "Failed to communicate with user port");
        ResponseEntity<CustomErrorResponse> response = handler.handleUserPort(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        CustomErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("AUTH_ERR_001", body.getErrorCode());
        assertEquals("Failed to communicate with user port", body.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus());
        assertNotNull(body.getTimestamp());
    }

    @Test
    void shouldHandleJWTException() {
        JWTException exception = new JWTException("JWT_ERR_001", "JWT token invalid");
        ResponseEntity<CustomErrorResponse> response = handler.handleJWT(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        CustomErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("JWT_ERR_001", body.getErrorCode());
        assertEquals("JWT token invalid", body.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus());
        assertNotNull(body.getTimestamp());
    }

}
