package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.utils.AdminCommonsUtils;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class AdministratorCrudServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AdminCommonsUtils adminCommonsUtils;

    @InjectMocks
    private AdministratorCrudServiceImpl service;

    private Administrator administrator;

    private AdministratorDTO administratorDTO;

    private AdministratorUpdateDTO administratorUpdateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        administrator = new Administrator();
        administrator.setId(1L);
        administrator.setUsername("adminUser");
        administrator.setEmail("admin@example.com");
        administrator.setPassword("password");
        administrator.setRole(UserEntity.UserRole.ADMIN);
        administrator.setActive(true);
        administrator.setNotifications(new ArrayList<>());

        administratorDTO = new AdministratorDTO();
        administratorDTO.setUsername("adminUser");
        administratorDTO.setEmail("admin@example.com");
        administratorDTO.setPassword("password");

        administratorUpdateDTO = new AdministratorUpdateDTO();
        administratorUpdateDTO.setUsername("updatedAdminUser");
    }

    @Test
    void testGetAdministratorSuccess() {
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            when(adminCommonsUtils.findAdministratorById(1L)).thenReturn(administrator);
            mockedStatic.when(() -> ConversionUtils.convertToDTO(administrator, AdministratorDTO.class))
                    .thenReturn(administratorDTO);

            AdministratorDTO result = service.getAdministrator(1L);

            assertEquals("adminUser", result.getUsername());
            assertEquals("admin@example.com", result.getEmail());
        }
    }

    @Test
    void testGetAdministratorNotFound() {
        when(adminCommonsUtils.findAdministratorById(1L)).thenThrow(new ResourceNotFoundException("Admin not found", "404"));

        assertThrows(ResourceNotFoundException.class, () -> service.getAdministrator(1L));
    }

    @Test
    void testCreateAdministratorSuccess() {
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToEntity(administratorDTO, Administrator.class))
                    .thenReturn(administrator);

            when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
            when(adminCommonsUtils.saveAndConvertToDTO(any(Administrator.class))).thenReturn(administratorDTO);

            AdministratorDTO result = service.createAdministrator(administratorDTO);

            assertEquals("adminUser", result.getUsername());
            assertEquals("encodedPassword", administrator.getPassword());
        }
    }

    @Test
    void testUpdateAdministratorSuccess() {
        when(adminCommonsUtils.findAdministratorById(1L)).thenReturn(administrator);
        when(adminCommonsUtils.saveAndConvertToDTO(administrator)).thenReturn(administratorDTO);

        AdministratorDTO result = service.updateAdministrator(1L, administratorUpdateDTO);

        assertEquals("adminUser", result.getUsername());
    }

    @Test
    void testToggleAdministratorStatusSuccess() {
        administrator.setActive(true);
        when(adminCommonsUtils.findAdministratorById(1L)).thenReturn(administrator);
        when(adminCommonsUtils.saveAndConvertToDTO(administrator)).thenReturn(administratorDTO);

        AdministratorDTO result = service.toggleAdministratorStatus(1L);

        assertFalse(administrator.isActive());
        assertEquals("adminUser", result.getUsername());
    }

    @Test
    void testToggleAdministratorStatusNotFound() {
        when(adminCommonsUtils.findAdministratorById(1L)).thenThrow(new ResourceNotFoundException("Admin not found", "404"));

        assertThrows(ResourceNotFoundException.class, () -> service.toggleAdministratorStatus(1L));
    }
}
