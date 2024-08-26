package com.ufro.dci.etransparency.etransparency_api_user.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.AlreadyExistingProcessException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.AlreadyExistingSurveyOnProcessException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.AlreadyRunningProcessException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.AssignmentConflictException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.CustomErrorResponse;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.DimensionEvidenceCapacityException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.FileProcessingException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.FileSizeExceededException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.FileTypeNotSupportedException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.RejectedProcessException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.TimeOutException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(". ");
        }
        CustomErrorResponse response = new CustomErrorResponse("BAD_REQUEST", errorMessage.toString(),
                HttpStatus.BAD_REQUEST.value());
        logger.error("Validation exception: {}", errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception exception) {
        CustomErrorResponse response = new CustomErrorResponse("INTERNAL_SERVER_ERROR",
                "An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error("Generic exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyExistingProcessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomErrorResponse> handleAlreadyExistingProcessException(
            AlreadyExistingProcessException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.CONFLICT.value());
        logger.error("Already existing process exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistingSurveyOnProcessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomErrorResponse> handleAlreadyExistingSurveyOnProcessException(
            AlreadyExistingSurveyOnProcessException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.CONFLICT.value());
        logger.error("Already existing survey on process exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.NOT_FOUND.value());
        logger.error("Resource not found exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeOutException.class)
    public ResponseEntity<CustomErrorResponse> handleTimeOutException(TimeOutException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.REQUEST_TIMEOUT.value());
        logger.error("Timeout exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(AssignmentConflictException.class)
    public ResponseEntity<CustomErrorResponse> handleAssignmentConflictException(
            AssignmentConflictException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.CONFLICT.value());
        logger.error("Assignment conflict exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RejectedProcessException.class)
    public ResponseEntity<CustomErrorResponse> handleRejectedProcessException(RejectedProcessException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.CONFLICT.value());
        logger.error("Rejected Process exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyRunningProcessException.class)
    public ResponseEntity<CustomErrorResponse> handleAlreadyRunningProcessException(
            AlreadyRunningProcessException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.CONFLICT.value());
        logger.error("Already running process exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileSizeExceededException.class)
    public ResponseEntity<CustomErrorResponse> handleFileSizeExceededException(FileSizeExceededException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.EXPECTATION_FAILED.value());
        logger.error("File size exceeded exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(FileTypeNotSupportedException.class)
    public ResponseEntity<CustomErrorResponse> handleFileTypeNotSupportedException(
            FileTypeNotSupportedException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.EXPECTATION_FAILED.value());
        logger.error("File type not supported exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(DimensionEvidenceCapacityException.class)
    public ResponseEntity<CustomErrorResponse> handleDimensionEvidenceCapacityException(
            DimensionEvidenceCapacityException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        logger.error("Exceeded amount of evidence on dimension: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        CustomErrorResponse response = new CustomErrorResponse("ACCESS_DENIED",
                "You do not have permission to access this resource.", HttpStatus.FORBIDDEN.value());
        logger.error("Access denied exception: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<CustomErrorResponse> handleFileProcessingException(FileProcessingException exception) {
        CustomErrorResponse response = new CustomErrorResponse(exception.getErrorCode(), exception.getMessage(),
                HttpStatus.EXPECTATION_FAILED.value());
        logger.error("Error processing the file: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

}
