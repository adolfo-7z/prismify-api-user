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
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom.EmailTemplateLoaderException;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom.MailSendException;
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

    @Test
    void shouldHandleMailSendException() {
        String errorCode = "MAIL_SEND_ERROR";
        String errorMessage = "Failed to send email.";
        MailSendException exception = new MailSendException(errorCode, errorMessage);
        ResponseEntity<CustomErrorResponse> responseEntity = handler.handleMailSend(exception);
        assertEquals(HttpStatus.BAD_GATEWAY, responseEntity.getStatusCode());
        CustomErrorResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorCode, response.getErrorCode());
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.BAD_GATEWAY.value(), response.getStatus());
    }

    @Test
    void shouldHandleEmailTemplateLoaderException() {
        String errorCode = "TEMPLATE_LOAD_ERROR";
        String errorMessage = "Email template could not be loaded.";
        EmailTemplateLoaderException exception = new EmailTemplateLoaderException(errorCode, errorMessage);
        ResponseEntity<CustomErrorResponse> responseEntity = handler.handleEmailTemplateLoader(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        CustomErrorResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(errorCode, response.getErrorCode());
        assertEquals(errorMessage, response.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

}
