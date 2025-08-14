package com.ufro.dci.etransparency.etransparency_api_user.services.auditor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.auditor.utils.AuditorCommonsUtils;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuditorCrudServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuditorCommonsUtils auditorCommonsUtils;

    @Mock
    private AuditorRepository auditorRepository;

    @InjectMocks
    private AuditorCrudServiceImpl service;

    private Auditor auditor;

    private AuditorDTO auditorDTO;

    private AuditorUpdateDTO auditorUpdateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        auditor = new Auditor();
        auditor.setId(1L);
        auditor.setUsername("auditorUser");
        auditor.setEmail("auditor@example.com");
        auditor.setPassword("password");
        auditor.setRole(UserRole.AUDITOR);
        auditor.setActive(true);
        auditor.setNAuditsPerformed(5L);
        auditor.setNAssignedInstitutions(2L);
        auditor.setCity("CityName");
        auditor.setCreatedAt(new Date());
        auditor.setUpdatedAt(new Date());
        auditor.setNotifications(new ArrayList<>());

        auditorDTO = new AuditorDTO();
        auditorDTO.setUsername("auditorUser");
        auditorDTO.setEmail("auditor@example.com");
        auditorDTO.setPassword("password");

        auditorUpdateDTO = new AuditorUpdateDTO();
        auditorUpdateDTO.setUsername("updatedAuditorUser");
    }

    @Test
    void testGetAuditorSuccess() {
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            when(auditorCommonsUtils.findAuditorById(1L)).thenReturn(auditor);
            mockedStatic.when(() -> ConversionUtils.convertToDTO(auditor, AuditorDTO.class))
                    .thenReturn(auditorDTO);

            AuditorDTO result = service.getAuditor(1L);

            assertEquals("auditorUser", result.getUsername());
            assertEquals("auditor@example.com", result.getEmail());
        }
    }

    @Test
    void testGetAuditorNotFound() {
        when(auditorCommonsUtils.findAuditorById(1L)).thenThrow(new ResourceNotFoundException("Auditor not found", "404"));

        assertThrows(ResourceNotFoundException.class, () -> service.getAuditor(1L));
    }

    @Test
    void testCreateAuditorSuccess() {
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToEntity(auditorDTO, Auditor.class))
                    .thenReturn(auditor);

            when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
            when(auditorCommonsUtils.saveAndConvertDTO(any(Auditor.class))).thenReturn(auditorDTO);

            AuditorDTO result = service.createAuditor(auditorDTO);

            assertEquals("auditorUser", result.getUsername());
            assertEquals("encodedPassword", auditor.getPassword());
        }
    }

    @Test
    void testUpdateAuditorSuccess() {
        when(auditorCommonsUtils.findAuditorById(1L)).thenReturn(auditor);
        when(auditorCommonsUtils.saveAndConvertDTO(auditor)).thenReturn(auditorDTO);

        AuditorDTO result = service.updateAuditor(1L, auditorUpdateDTO);

        assertEquals("auditorUser", result.getUsername());
    }

    @Test
    void testToggleAuditorStatusSuccess() {
        auditor.setActive(true);
        when(auditorCommonsUtils.findAuditorById(1L)).thenReturn(auditor);
        when(auditorCommonsUtils.saveAndConvertDTO(auditor)).thenReturn(auditorDTO);

        AuditorDTO result = service.toggleAuditorStatus(1L);

        assertFalse(auditor.isActive());
        assertEquals("auditorUser", result.getUsername());
    }

    @Test
    void testToggleAuditorStatusNotFound() {
        when(auditorCommonsUtils.findAuditorById(1L)).thenThrow(new ResourceNotFoundException("Auditor not found", "404"));

        assertThrows(ResourceNotFoundException.class, () -> service.toggleAuditorStatus(1L));
    }
}
