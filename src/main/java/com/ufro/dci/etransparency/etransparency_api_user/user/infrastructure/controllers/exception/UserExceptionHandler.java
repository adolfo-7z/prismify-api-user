package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ufro.dci.etransparency.etransparency_api_user.commons.exception.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UpdateMapperException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserNotFoundException;

@RestControllerAdvice
public class UserExceptionHandler {

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<CustomErrorResponse> handleUserNotFound(
                        UserNotFoundException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(UpdateMapperException.class)
        public ResponseEntity<CustomErrorResponse> handleUpdateMapper(
                        UpdateMapperException exception) {
                CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
