package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom.UserPortException;

@ExtendWith(MockitoExtension.class)
class MailExceptionHandlerTest {

    @InjectMocks
    private MailExceptionHandler handler;

    @Test
    void shouldHandleUserPortException() {
        String errorCode = "USER_NOT_FOUND";
        String errorMessage = "The user was not found.";
        UserPortException exception = new UserPortException(errorCode, errorMessage);
        ResponseEntity<CustomErrorResponse> responseEntity = handler.handleUserPort(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        CustomErrorResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorCode, response.getErrorCode());
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

}
