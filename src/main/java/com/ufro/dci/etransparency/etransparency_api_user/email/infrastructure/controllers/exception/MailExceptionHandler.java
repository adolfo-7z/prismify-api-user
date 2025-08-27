package com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.email.infrastructure.controllers.exception.custom.UserPortException;

@RestControllerAdvice
public class MailExceptionHandler {

        @ExceptionHandler(UserPortException.class)
        public ResponseEntity<CustomErrorResponse> handleUserPort(
                        UserPortException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
