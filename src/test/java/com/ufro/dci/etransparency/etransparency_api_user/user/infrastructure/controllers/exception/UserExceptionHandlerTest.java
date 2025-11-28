package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.responses.ApiResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.*;

@ExtendWith(MockitoExtension.class)
class UserExceptionHandlerTest {

    @InjectMocks
    private UserExceptionHandler handler;

    @Test
    void shouldHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("USER_ERR_001",
                "User ID 10101 not detected");
        ResponseEntity<ApiResponse<Object>> response = handler.handleUserNotFound(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ApiResponse<Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals("USER_ERR_001", body.error().code());
        assertEquals("User ID 10101 not detected", body.error().message());
        assertNull(body.data());
    }

    @Test
    void shouldHandleUpdateMapperException() {
        UpdateMapperException exception = new UpdateMapperException("MAPPING_FAILURE",
                "Failed to transfer data");
        ResponseEntity<ApiResponse<Object>> response = handler.handleUpdateMapper(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiResponse<Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals("MAPPING_FAILURE", body.error().code());
        assertEquals("Failed to transfer data", body.error().message());
    }

    @Test
    void shouldHandleInvalidRecoveryCodeException() {
        InvalidRecoveryCodeException exception = new InvalidRecoveryCodeException(
                "RECOVERY_CODE_INVALID",
                "The recovery code is not valid");
        ResponseEntity<ApiResponse<Object>> response = handler.handleInvalidRecoveryCode(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiResponse<Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals("RECOVERY_CODE_INVALID", body.error().code());
        assertEquals("The recovery code is not valid", body.error().message());
    }

    @Test
    void shouldHandleInvalidPasswordException() {
        InvalidPasswordException exception = new InvalidPasswordException(
                "INVALID_PASSWORD",
                "Password does not meet requirements");
        ResponseEntity<ApiResponse<Object>> response = handler.handleInvalidPassword(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiResponse<Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals("INVALID_PASSWORD", body.error().code());
        assertEquals("Password does not meet requirements", body.error().message());
    }

    @Test
    void shouldHandleUserEmailPortException() {
        UserEmailPortException exception = new UserEmailPortException(
                "EMAIL_PORT_FAILURE",
                "Failed to send confirmation email");
        ResponseEntity<ApiResponse<Object>> response = handler.handleUserEmailPort(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiResponse<Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("error", body.status());
        assertEquals("EMAIL_PORT_FAILURE", body.error().code());
        assertEquals("Failed to send confirmation email", body.error().message());
    }

}
