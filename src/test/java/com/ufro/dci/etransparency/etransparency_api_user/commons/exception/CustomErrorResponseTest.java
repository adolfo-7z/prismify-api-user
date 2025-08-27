package com.ufro.dci.etransparency.etransparency_api_user.commons.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class CustomErrorResponseTest {

    @Test
    void constructor_ShouldSetFieldsCorrectly() {
        String expectedCode = "ERR-001";
        String expectedMessage = "An error occurred";
        int expectedStatus = 400;
        CustomErrorResponse errorResponse = new CustomErrorResponse(expectedCode, expectedMessage, expectedStatus);
        assertEquals(expectedCode, errorResponse.getErrorCode());
        assertEquals(expectedMessage, errorResponse.getMessage());
        assertEquals(expectedStatus, errorResponse.getStatus());
        LocalDateTime parsedTimestamp = LocalDateTime.parse(errorResponse.getTimestamp(),
                DateTimeFormatter.ISO_DATE_TIME);
        assertNotNull(parsedTimestamp, "Timestamp should be parseable as ISO_DATE_TIME");
    }

    @Test
    void timestamp_ShouldBeCloseToNow() {
        LocalDateTime before = LocalDateTime.now();
        CustomErrorResponse errorResponse = new CustomErrorResponse("ERR-002", "Something went wrong", 500);
        LocalDateTime after = LocalDateTime.now();
        LocalDateTime responseTime = LocalDateTime.parse(errorResponse.getTimestamp(), DateTimeFormatter.ISO_DATE_TIME);
        assertTrue((!responseTime.isBefore(before)) && (!responseTime.isAfter(after)),
                "Timestamp should be within the creation window");
    }

    @Test
    void gettersAndSetters_ShouldWorkProperly() {
        CustomErrorResponse errorResponse = new CustomErrorResponse("INIT", "Initial", 200);
        errorResponse.setErrorCode("ERR-003");
        errorResponse.setMessage("Updated message");
        errorResponse.setStatus(404);
        errorResponse.setTimestamp("2025-07-31T12:00:00");
        assertEquals("ERR-003", errorResponse.getErrorCode());
        assertEquals("Updated message", errorResponse.getMessage());
        assertEquals(404, errorResponse.getStatus());
        assertEquals("2025-07-31T12:00:00", errorResponse.getTimestamp());
    }

}
