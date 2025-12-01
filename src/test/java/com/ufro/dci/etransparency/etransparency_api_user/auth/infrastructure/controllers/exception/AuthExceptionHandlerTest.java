package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.JWTException;
import com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.controllers.exception.custom.UserPortException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.responses.ApiResponse;

class AuthExceptionHandlerTest {

    private AuthExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AuthExceptionHandler();
    }

    @Test
    void shouldHandleUserPortException() {
        UserPortException exception = new UserPortException("AUTH_ERR_001", "Failed to communicate with user port");
        ResponseEntity<ApiResponse<Object>> response = handler.handleUserPort(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiResponse<Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals("AUTH_ERR_001", body.error().code());
        assertEquals("Failed to communicate with user port", body.error().message());
        assertNull(body.data());
    }

    @Test
    void shouldHandleJWTException() {
        JWTException exception = new JWTException("JWT_ERR_001", "JWT token invalid");
        ResponseEntity<ApiResponse<Object>> response = handler.handleJWT(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiResponse<Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals("JWT_ERR_001", body.error().code());
        assertEquals("JWT token invalid", body.error().message());
        assertNull(body.data());
    }

}
