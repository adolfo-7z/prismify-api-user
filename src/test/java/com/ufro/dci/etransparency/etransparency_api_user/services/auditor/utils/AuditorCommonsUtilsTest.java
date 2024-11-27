package com.ufro.dci.etransparency.etransparency_api_user.services.auditor.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.auditor.AuditorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

class AuditorCommonsUtilsTest {

    @Mock
    private AuditorRepository auditorRepository;

    @InjectMocks
    private AuditorCommonsUtils auditorCommonsUtils;

    private Auditor auditor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        auditor = new Auditor();
        auditor.setId(1L);
        auditor.setUsername("auditorUser");
        auditor.setEmail("auditor@example.com");
        auditor.setActive(true);
    }

    @Test
    void testFindAuditorByIdSuccess() {
        when(auditorRepository.findById(1L)).thenReturn(Optional.of(auditor));

        Auditor result = auditorCommonsUtils.findAuditorById(1L);

        assertNotNull(result);
        assertEquals("auditorUser", result.getUsername());
        verify(auditorRepository).findById(1L);
    }

    @Test
    void testFindAuditorByIdNotFound() {
        when(auditorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> auditorCommonsUtils.findAuditorById(1L));

        verify(auditorRepository).findById(1L);
    }

    @Test
    void testFindAuditorByIdInactive() {
        auditor.setActive(false);
        when(auditorRepository.findById(1L)).thenReturn(Optional.of(auditor));

        assertThrows(ResourceNotFoundException.class, () -> auditorCommonsUtils.findAuditorById(1L));

        verify(auditorRepository).findById(1L);
    }

    @Test
    void testSaveAndConvertDTOSuccess() {
        Auditor savedAuditor = new Auditor();
        savedAuditor.setId(1L);
        savedAuditor.setUsername("auditorUser");
        savedAuditor.setEmail("auditor@example.com");

        AuditorDTO auditorDTO = new AuditorDTO();
        auditorDTO.setUsername("auditorUser");
        auditorDTO.setEmail("auditor@example.com");

        when(auditorRepository.save(auditor)).thenReturn(savedAuditor);

        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(savedAuditor, AuditorDTO.class))
                    .thenReturn(auditorDTO);

            AuditorDTO result = auditorCommonsUtils.saveAndConvertDTO(auditor);

            assertNotNull(result);
            assertEquals("auditorUser", result.getUsername());
            verify(auditorRepository).save(auditor);
        }
    }
}
