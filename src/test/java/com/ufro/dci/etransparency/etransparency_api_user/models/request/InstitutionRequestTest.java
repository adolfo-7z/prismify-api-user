package com.ufro.dci.etransparency.etransparency_api_user.models.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class InstitutionRequestTest {

    @Test
    void testInstitutionRequestConstructor() {
        InstitutionRequest request = new InstitutionRequest();

        assertNull(request.getId());
        assertNull(request.getInstitutionName());
        assertNull(request.getPhoneNumber());
        assertNull(request.getManagerEmail());
        assertNull(request.getRequestStatus());
        assertNull(request.getCreatedAt());
        assertNull(request.getUpdatedAt());
    }

    @Test
    void testSettersAndGetters() {
        InstitutionRequest request = new InstitutionRequest();

        request.setId(1L);
        request.setInstitutionName("Test Institution");
        request.setPhoneNumber("123456789");
        request.setManagerEmail("manager@example.com");
        request.setRequestStatus(InstitutionRequest.RequestStatus.UNREAD);
        Date createdAt = new Date();
        Date updatedAt = new Date();
        request.setCreatedAt(createdAt);
        request.setUpdatedAt(updatedAt);

        assertEquals(1L, request.getId());
        assertEquals("Test Institution", request.getInstitutionName());
        assertEquals("123456789", request.getPhoneNumber());
        assertEquals("manager@example.com", request.getManagerEmail());
        assertEquals(InstitutionRequest.RequestStatus.UNREAD, request.getRequestStatus());
        assertEquals(createdAt, request.getCreatedAt());
        assertEquals(updatedAt, request.getUpdatedAt());
    }

    @Test
    void testRequestStatusEnum() {
        InstitutionRequest request = new InstitutionRequest();

        request.setRequestStatus(InstitutionRequest.RequestStatus.READ);
        assertEquals(InstitutionRequest.RequestStatus.READ, request.getRequestStatus());

        request.setRequestStatus(InstitutionRequest.RequestStatus.UNREAD);
        assertEquals(InstitutionRequest.RequestStatus.UNREAD, request.getRequestStatus());
    }

    @Test
    void testSetDates() {
        InstitutionRequest request = new InstitutionRequest();
        Date createdAt = new Date();
        Date updatedAt = new Date();

        request.setCreatedAt(createdAt);
        request.setUpdatedAt(updatedAt);

        assertEquals(createdAt, request.getCreatedAt());
        assertEquals(updatedAt, request.getUpdatedAt());
    }
}
