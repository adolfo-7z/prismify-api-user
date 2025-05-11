package com.ufro.dci.etransparency.etransparency_api_user.services.manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.institution.InstitutionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.manager.ManagerUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.services.manager.utils.ManagerCommonsUtils;
import com.ufro.dci.etransparency.etransparency_api_user.utils.ConversionUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class ManagerCrudServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ManagerCommonsUtils managerCommonsUtils;

    @InjectMocks
    private ManagerCrudServiceImpl service;

    private Manager manager;

    private ManagerDTO managerDTO;

    private ManagerUpdateDTO managerUpdateDTO;

    private Institution institution;

    private InstitutionDTO institutionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        institution = new Institution();
        institution.setId(1L);
        institution.setName("Test Institution");

        institutionDTO = new InstitutionDTO();
        institutionDTO.setName("Test Institution");

        manager = new Manager();
        manager.setId(1L);
        manager.setUsername("managerUser");
        manager.setEmail("manager@example.com");
        manager.setPassword("password");
        manager.setRole(UserRole.MANAGER);
        manager.setActive(true);
        manager.setPosition("Test Position");
        manager.setRut("12345678-9");
        manager.setInstitution(institution);
        manager.setNotifications(new ArrayList<>());

        managerDTO = new ManagerDTO();
        managerDTO.setUsername("managerUser");
        managerDTO.setEmail("manager@example.com");
        managerDTO.setPassword("password");

        managerUpdateDTO = new ManagerUpdateDTO();
        managerUpdateDTO.setUsername("updatedManagerUser");
    }

    @Test
    void testGetManagerSuccess() {
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            when(managerCommonsUtils.findManagerById(1L)).thenReturn(manager);
            mockedStatic.when(() -> ConversionUtils.convertToDTO(manager, ManagerDTO.class)).thenReturn(managerDTO);
            mockedStatic.when(() -> ConversionUtils.convertToDTO(institution, InstitutionDTO.class))
                    .thenReturn(institutionDTO);

            ManagerDTO result = service.getManager(1L);

            assertEquals("managerUser", result.getUsername());
            assertEquals("manager@example.com", result.getEmail());
            assertNotNull(result.getInstitution());
            assertEquals("Test Institution", result.getInstitution().getName());
        }
    }

    @Test
    void testGetManagerNotFound() {
        when(managerCommonsUtils.findManagerById(1L)).thenThrow(new ResourceNotFoundException("Manager not found", "404"));

        assertThrows(ResourceNotFoundException.class, () -> service.getManager(1L));
    }

    @Test
    void testCreateManagerSuccess() {
        try (MockedStatic<ConversionUtils> mockedStatic = mockStatic(ConversionUtils.class)) {
            mockedStatic.when(() -> ConversionUtils.convertToEntity(managerDTO, Manager.class)).thenReturn(manager);
            when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
            when(managerCommonsUtils.saveAndConvertToDTO(any(Manager.class))).thenReturn(managerDTO);

            ManagerDTO result = service.createManager(managerDTO);

            assertEquals("managerUser", result.getUsername());
            assertEquals("encodedPassword", manager.getPassword());
        }
    }

    @Test
    void testUpdateManagerSuccess() {
        when(managerCommonsUtils.findManagerById(1L)).thenReturn(manager);
        when(managerCommonsUtils.saveAndConvertToDTO(manager)).thenReturn(managerDTO);

        ManagerDTO result = service.updateManager(1L, managerUpdateDTO);

        assertEquals("managerUser", result.getUsername());
    }

    @Test
    void testToggleManagerStatusSuccess() {
        manager.setActive(true);
        when(managerCommonsUtils.findManagerById(1L)).thenReturn(manager);
        when(managerCommonsUtils.saveAndConvertToDTO(manager)).thenReturn(managerDTO);

        ManagerDTO result = service.toggleManagerStatus(1L);

        assertFalse(manager.isActive());
        assertEquals("managerUser", result.getUsername());
    }

    @Test
    void testToggleManagerStatusNotFound() {
        when(managerCommonsUtils.findManagerById(1L)).thenThrow(new ResourceNotFoundException("Manager not found", "404"));

        assertThrows(ResourceNotFoundException.class, () -> service.toggleManagerStatus(1L));
    }
}
