package com.ufro.dci.etransparency.etransparency_api_user.services.administrator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.administrator.AdministratorUpdateDTO;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.services.administrator.AdministratorCrudServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministratorCrudServiceImplTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdministratorCrudServiceImpl administratorCrudService;

    private Administrator administrator;
    private AdministratorDTO administratorDTO;
    private AdministratorUpdateDTO administratorUpdateDTO;

    @BeforeEach
    void setUp() {
        administrator = new Administrator();
        administrator.setId(1L);
        administrator.setUsername("admin");
        administrator.setActive(true);

        administratorDTO = new AdministratorDTO();
        administratorDTO.setId(1L);
        administratorDTO.setUsername("admin");
        administratorDTO.setPassword("password");
        administratorDTO.setRole(UserRole.ADMIN);

        administratorUpdateDTO = new AdministratorUpdateDTO();
    }

    @Test
    void getAdministrator_WhenAdministratorIsActive_ShouldReturnAdministratorDTO() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(administrator));

        AdministratorDTO result = administratorCrudService.getAdministrator(1L);

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        verify(administratorRepository, times(1)).findById(1L);
    }

    @Test
    void getAdministrator_WhenAdministratorIsInactive_ShouldThrowException() {
        administrator.setActive(false);
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(administrator));

        assertThrows(ResourceNotFoundException.class, () -> administratorCrudService.getAdministrator(1L));

        verify(administratorRepository, times(1)).findById(1L);
    }

    @Test
    void createAdministrator_ShouldReturnCreatedAdministratorDTO() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(administratorRepository.save(any(Administrator.class))).thenAnswer(invocation -> {
            Administrator admin = invocation.getArgument(0);
            admin.setId(1L); // Asigna un ID al administrador simulado
            return admin;
        });

        AdministratorDTO result = administratorCrudService.createAdministrator(administratorDTO);

        assertNotNull(result);
        assertEquals(UserRole.ADMIN, result.getRole()); // Verifica que el rol sea ADMIN
        verify(administratorRepository, times(1)).save(any(Administrator.class));
    }


    @Test
    void updateAdministrator_WhenAdministratorIsActive_ShouldReturnUpdatedAdministratorDTO() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(administrator));
        when(administratorRepository.save(any(Administrator.class))).thenReturn(administrator);

        AdministratorDTO result = administratorCrudService.updateAdministrator(1L, administratorUpdateDTO);

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        verify(administratorRepository, times(1)).findById(1L);
        verify(administratorRepository, times(1)).save(any(Administrator.class));
    }

    @Test
    void updateAdministrator_WhenAdministratorIsInactive_ShouldThrowException() {
        administrator.setActive(false);
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(administrator));

        assertThrows(ResourceNotFoundException.class,
                () -> administratorCrudService.updateAdministrator(1L, administratorUpdateDTO));

        verify(administratorRepository, times(1)).findById(1L);
    }

    @Test
    void toggleAdministratorStatus_ShouldReturnAdministratorWithToggledStatus() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(administrator));
        when(administratorRepository.save(any(Administrator.class))).thenReturn(administrator);

        AdministratorDTO result = administratorCrudService.toggleAdministratorStatus(1L);

        assertNotNull(result);
        assertFalse(result.isActive());
        verify(administratorRepository, times(1)).findById(1L);
        verify(administratorRepository, times(1)).save(any(Administrator.class));
    }

    @Test
    void toggleAdministratorStatus_WhenAdministratorNotFound_ShouldThrowException() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> administratorCrudService.toggleAdministratorStatus(1L));

        verify(administratorRepository, times(1)).findById(1L);
    }
}
