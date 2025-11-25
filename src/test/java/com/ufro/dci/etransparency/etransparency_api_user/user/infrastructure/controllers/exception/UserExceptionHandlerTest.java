package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.InvalidPasswordException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.InvalidRecoveryCodeException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UpdateMapperException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserEmailPortException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserExceptionHandlerTest {

    @InjectMocks
    private UserExceptionHandler handler;

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

    @Test
    void shouldHandleInvalidRecoveryCodeException() {
        InvalidRecoveryCodeException exception = new InvalidRecoveryCodeException(
                "RECOVERY_CODE_INVALID",
                "The recovery code is not valid");
        ResponseEntity<CustomErrorResponse> response = handler.handleInvalidRecoveryCode(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        CustomErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("RECOVERY_CODE_INVALID", body.getErrorCode());
        assertEquals("The recovery code is not valid", body.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
    }

    @Test
    void shouldHandleInvalidPasswordException() {
        InvalidPasswordException exception = new InvalidPasswordException(
                "INVALID_PASSWORD",
                "Password does not meet requirements");
        ResponseEntity<CustomErrorResponse> response = handler.handleInvalidPassword(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        CustomErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("INVALID_PASSWORD", body.getErrorCode());
        assertEquals("Password does not meet requirements", body.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus());
    }

    @Test
    void shouldHandleUserEmailPortException() {
        UserEmailPortException exception = new UserEmailPortException(
                "EMAIL_PORT_FAILURE",
                "Failed to send confirmation email");
        ResponseEntity<CustomErrorResponse> response = handler.handleUserEmailPort(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        CustomErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("EMAIL_PORT_FAILURE", body.getErrorCode());
        assertEquals("Failed to send confirmation email", body.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus());
    }

}
