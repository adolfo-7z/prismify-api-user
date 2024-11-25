package com.ufro.dci.etransparency.etransparency_api_user.exceptions;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setup() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequest() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("field", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("BAD_REQUEST", response.getBody().getErrorCode());
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getErrorCode());
    }

    @Test
    void handleAlreadyExistingProcessException_ShouldReturnConflict() {
        AlreadyExistingProcessException exception = new AlreadyExistingProcessException("PROCESS_EXISTS", "Process already exists");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleAlreadyExistingProcessException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("PROCESS_EXISTS", response.getBody().getErrorCode());
    }

    @Test
    void handleResourceNotFoundException_ShouldReturnNotFound() {
        ResourceNotFoundException exception = new ResourceNotFoundException("NOT_FOUND", "Resource not found");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("NOT_FOUND", response.getBody().getErrorCode());
    }

    @Test
    void handleTimeOutException_ShouldReturnRequestTimeout() {
        TimeOutException exception = new TimeOutException("TIMEOUT", "Request timed out");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleTimeOutException(exception);

        assertEquals(HttpStatus.REQUEST_TIMEOUT, response.getStatusCode());
        assertEquals("TIMEOUT", response.getBody().getErrorCode());
    }

    @Test
    void handleFileSizeExceededException_ShouldReturnExpectationFailed() {
        FileSizeExceededException exception = new FileSizeExceededException("FILE_TOO_LARGE", "File size exceeded");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleFileSizeExceededException(exception);

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals("FILE_TOO_LARGE", response.getBody().getErrorCode());
    }

    @Test
    void handleAccessDeniedException_ShouldReturnForbidden() {
        AccessDeniedException exception = new AccessDeniedException("Access denied");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleAccessDeniedException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("ACCESS_DENIED", response.getBody().getErrorCode());
    }

    @Test
    void handleFileProcessingException_ShouldReturnExpectationFailed() {
        FileProcessingException exception = new FileProcessingException("FILE_ERROR", "Error processing file");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleFileProcessingException(exception);

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals("FILE_ERROR", response.getBody().getErrorCode());
    }

    @Test
    void handleCustomConversionException_ShouldReturnInternalServerError() {
        CustomConversionException exception = new CustomConversionException("CONVERSION_ERROR", "Error converting resource");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleCustomConversionException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("CONVERSION_ERROR", response.getBody().getErrorCode());
    }

    @Test
    void handleAssignmentConflictException_ShouldReturnConflict() {
        AssignmentConflictException exception = new AssignmentConflictException("ASSIGNMENT_CONFLICT", "Assignment conflict occurred");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleAssignmentConflictException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("ASSIGNMENT_CONFLICT", response.getBody().getErrorCode());
    }

    @Test
    void handleRejectedProcessException_ShouldReturnConflict() {
        RejectedProcessException exception = new RejectedProcessException("PROCESS_REJECTED", "Process was rejected");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleRejectedProcessException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("PROCESS_REJECTED", response.getBody().getErrorCode());
    }

    @Test
    void handleAlreadyRunningProcessException_ShouldReturnConflict() {
        AlreadyRunningProcessException exception = new AlreadyRunningProcessException("RUNNING_PROCESS", "Process is already running");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleAlreadyRunningProcessException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("RUNNING_PROCESS", response.getBody().getErrorCode());
    }

    @Test
    void handleFileTypeNotSupportedException_ShouldReturnExpectationFailed() {
        FileTypeNotSupportedException exception = new FileTypeNotSupportedException("UNSUPPORTED_FILE_TYPE", "File type is not supported");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleFileTypeNotSupportedException(exception);

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals("UNSUPPORTED_FILE_TYPE", response.getBody().getErrorCode());
    }

    @Test
    void handleDimensionEvidenceCapacityException_ShouldReturnBadRequest() {
        DimensionEvidenceCapacityException exception = new DimensionEvidenceCapacityException("EVIDENCE_CAPACITY", "Exceeded evidence capacity for dimension");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleDimensionEvidenceCapacityException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("EVIDENCE_CAPACITY", response.getBody().getErrorCode());
    }

}
