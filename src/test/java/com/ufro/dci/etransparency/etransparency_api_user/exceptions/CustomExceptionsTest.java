package com.ufro.dci.etransparency.etransparency_api_user.exceptions;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionsTest {

    @Test
    void testAlreadyExistingProcessException() {
        AlreadyExistingProcessException exception = new AlreadyExistingProcessException("PROCESS_EXISTS", "Process already exists");
        assertEquals("PROCESS_EXISTS", exception.getErrorCode());
        assertEquals("Process already exists", exception.getMessage());
    }

    @Test
    void testAlreadyExistingSurveyOnProcessException() {
        AlreadyExistingSurveyOnProcessException exception = new AlreadyExistingSurveyOnProcessException("SURVEY_EXISTS", "Survey already exists on process");
        assertEquals("SURVEY_EXISTS", exception.getErrorCode());
        assertEquals("Survey already exists on process", exception.getMessage());
    }

    @Test
    void testAlreadyRunningProcessException() {
        AlreadyRunningProcessException exception = new AlreadyRunningProcessException("RUNNING_PROCESS", "Process is already running");
        assertEquals("RUNNING_PROCESS", exception.getErrorCode());
        assertEquals("Process is already running", exception.getMessage());
    }

    @Test
    void testAssignmentConflictException() {
        AssignmentConflictException exception = new AssignmentConflictException("ASSIGNMENT_CONFLICT", "Assignment conflict occurred");
        assertEquals("ASSIGNMENT_CONFLICT", exception.getErrorCode());
        assertEquals("Assignment conflict occurred", exception.getMessage());
    }

    @Test
    void testCustomConversionException() {
        CustomConversionException exception = new CustomConversionException("CONVERSION_ERROR", "Error in resource conversion");
        assertEquals("CONVERSION_ERROR", exception.getErrorCode());
        assertEquals("Error in resource conversion", exception.getMessage());
    }

    @Test
    void testDimensionEvidenceCapacityException() {
        DimensionEvidenceCapacityException exception = new DimensionEvidenceCapacityException("EVIDENCE_CAPACITY", "Exceeded evidence capacity for dimension");
        assertEquals("EVIDENCE_CAPACITY", exception.getErrorCode());
        assertEquals("Exceeded evidence capacity for dimension", exception.getMessage());
    }

    @Test
    void testFileProcessingException() {
        FileProcessingException exception = new FileProcessingException("FILE_PROCESS_ERROR", "Error processing file");
        assertEquals("FILE_PROCESS_ERROR", exception.getErrorCode());
        assertEquals("Error processing file", exception.getMessage());
    }

    @Test
    void testFileSizeExceededException() {
        FileSizeExceededException exception = new FileSizeExceededException("FILE_TOO_LARGE", "File size exceeded limit");
        assertEquals("FILE_TOO_LARGE", exception.getErrorCode());
        assertEquals("File size exceeded limit", exception.getMessage());
    }

    @Test
    void testFileTypeNotSupportedException() {
        FileTypeNotSupportedException exception = new FileTypeNotSupportedException("UNSUPPORTED_FILE_TYPE", "File type is not supported");
        assertEquals("UNSUPPORTED_FILE_TYPE", exception.getErrorCode());
        assertEquals("File type is not supported", exception.getMessage());
    }


    @Test
    void testRejectedProcessException() {
        RejectedProcessException exception = new RejectedProcessException("PROCESS_REJECTED", "Process was rejected");
        assertEquals("PROCESS_REJECTED", exception.getErrorCode());
        assertEquals("Process was rejected", exception.getMessage());
    }

    @Test
    void testResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("NOT_FOUND", "Resource not found");
        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertEquals("Resource not found", exception.getMessage());
    }

    @Test
    void testTimeOutException() {
        TimeOutException exception = new TimeOutException("TIMEOUT", "Operation timed out");
        assertEquals("TIMEOUT", exception.getErrorCode());
        assertEquals("Operation timed out", exception.getMessage());
    }

    @Test
    void testCustomErrorResponse() {
        CustomErrorResponse response = new CustomErrorResponse("ERROR_CODE", "An error occurred", 400);
        assertEquals("ERROR_CODE", response.getErrorCode());
        assertEquals("An error occurred", response.getMessage());
        assertEquals(400, response.getStatus());
        assertNotNull(response.getTimestamp());
    }
}
