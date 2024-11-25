package com.ufro.dci.etransparency.etransparency_api_user.services.administrator.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

class AdminCommonsUtilsTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @InjectMocks
    private AdminCommonsUtils adminCommonsUtils;

    private Administrator administrator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        administrator = new Administrator();
        administrator.setId(1L);
        administrator.setUsername("adminUser");
        administrator.setEmail("admin@example.com");
    }

    @Test
    void testFindAdministratorByIdSuccess() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(administrator));

        Administrator result = adminCommonsUtils.findAdministratorById(1L);

        assertNotNull(result);
        assertEquals("adminUser", result.getUsername());
        verify(administratorRepository).findById(1L);
    }

    @Test
    void testFindAdministratorByIdNotFound() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> adminCommonsUtils.findAdministratorById(1L));

        verify(administratorRepository).findById(1L);
    }

    @Test
    void testSaveAndConvertToDTOSuccess() {
        Administrator savedAdministrator = new Administrator();
        savedAdministrator.setId(1L);
        savedAdministrator.setUsername("adminUser");
        savedAdministrator.setEmail("admin@example.com");

        AdministratorDTO administratorDTO = new AdministratorDTO();
        administratorDTO.setUsername("adminUser");
        administratorDTO.setEmail("admin@example.com");

        when(administratorRepository.save(administrator)).thenReturn(savedAdministrator);

        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToDTO(savedAdministrator, AdministratorDTO.class))
                    .thenReturn(administratorDTO);

            AdministratorDTO result = adminCommonsUtils.saveAndConvertToDTO(administrator);

            assertNotNull(result);
            assertEquals("adminUser", result.getUsername());
            verify(administratorRepository).save(administrator);
        }
    }
}
