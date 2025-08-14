package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class AuditorServiceImplTest {

    @Mock
    private AuditorRepository auditorRepository;

    @InjectMocks
    private AuditorServiceImpl service;

    private Auditor activeAuditor;
    private Auditor inactiveAuditor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activeAuditor = new Auditor();
        activeAuditor.setId(1L);
        activeAuditor.setUsername("activeAuditor");
        activeAuditor.setEmail("active@example.com");
        activeAuditor.setActive(true);
        inactiveAuditor = new Auditor();
        inactiveAuditor.setId(2L);
        inactiveAuditor.setUsername("inactiveAuditor");
        inactiveAuditor.setEmail("inactive@example.com");
        inactiveAuditor.setActive(false);
    }

    @Test
    void testGetAllAuditorsSuccess() {
        Page<Auditor> auditorPage = new PageImpl<>(List.of(activeAuditor, inactiveAuditor));
        when(auditorRepository.findAll(any(PageRequest.class))).thenReturn(auditorPage);
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(activeAuditor, AuditorDTO.class))
                    .thenReturn(new AuditorDTO());
            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) service.getAllAuditors(0, 10, "asc", null);
            assertNotNull(result);
            assertEquals(1, ((List<?>) result.get("auditors")).size());
            assertEquals(1, result.get("totalPages"));
        }
    }

    @Test
    void testGetAllAuditorsWithNameFilter() {
        Page<Auditor> auditorPage = new PageImpl<>(List.of(activeAuditor));
        when(auditorRepository.findByUsernameContainingIgnoreCase(eq("active"), any(PageRequest.class)))
                .thenReturn(auditorPage);
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(activeAuditor, AuditorDTO.class))
                    .thenReturn(new AuditorDTO());
            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) service.getAllAuditors(0, 10, "asc", "active");
            assertNotNull(result);
            assertEquals(1, ((List<?>) result.get("auditors")).size());
            assertEquals(1, result.get("totalPages"));
        }
    }

    @Test
    void testGetAllAuditorsWithDescendingSort() {
        Page<Auditor> auditorPage = new PageImpl<>(List.of(activeAuditor));
        when(auditorRepository.findAll(any(PageRequest.class))).thenReturn(auditorPage);
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(activeAuditor, AuditorDTO.class))
                    .thenReturn(new AuditorDTO());
            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) service.getAllAuditors(0, 10, "desc", null);
            assertNotNull(result);
            assertEquals(1, ((List<?>) result.get("auditors")).size());
            assertEquals(1, result.get("totalPages"));
        }
    }

    @Test
    void testGetAllAuditorsWithEmptyResult() {
        Page<Auditor> emptyPage = new PageImpl<>(Collections.emptyList());
        when(auditorRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);
        @SuppressWarnings("unchecked")
        Map<String, Object> result = (Map<String, Object>) service.getAllAuditors(0, 10, "asc", null);
        assertNotNull(result);
        assertTrue(((List<?>) result.get("auditors")).isEmpty());
        assertEquals(1, result.get("totalPages"));
    }
}
