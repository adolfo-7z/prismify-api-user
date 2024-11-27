package com.ufro.dci.etransparency.etransparency_api_user.services.manager.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

class ManagerCommonsUtilsTest {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerCommonsUtils managerCommonsUtils;

    private Manager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        manager = new Manager();
        manager.setId(1L);
        manager.setUsername("managerUser");
        manager.setEmail("manager@example.com");
        manager.setActive(true);
    }

    @Test
    void testFindManagerByIdSuccess() {
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));

        Manager result = managerCommonsUtils.findManagerById(1L);

        assertNotNull(result);
        assertEquals("managerUser", result.getUsername());
        verify(managerRepository).findById(1L);
    }

    @Test
    void testFindManagerByIdNotFound() {
        when(managerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> managerCommonsUtils.findManagerById(1L));

        verify(managerRepository).findById(1L);
    }

    @Test
    void testFindManagerByIdInactive() {
        manager.setActive(false);
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));

        assertThrows(ResourceNotFoundException.class, () -> managerCommonsUtils.findManagerById(1L));

        verify(managerRepository).findById(1L);
    }

    @Test
    void testSaveAndConvertToDTOSuccess() {
        Manager savedManager = new Manager();
        savedManager.setId(1L);
        savedManager.setUsername("managerUser");
        savedManager.setEmail("manager@example.com");

        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setUsername("managerUser");
        managerDTO.setEmail("manager@example.com");

        when(managerRepository.save(manager)).thenReturn(savedManager);

        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(savedManager, ManagerDTO.class))
                    .thenReturn(managerDTO);

            ManagerDTO result = managerCommonsUtils.saveAndConvertToDTO(manager);

            assertNotNull(result);
            assertEquals("managerUser", result.getUsername());
            verify(managerRepository).save(manager);
        }
    }
}
