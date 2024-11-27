package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.ufro.dci.etransparency.etransparency_api_user.config.security.models.CustomUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceImplTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private AuditorRepository auditorRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private UserDetailsServiceImpl service;

    private UserEntity adminUser;
    private UserEntity auditorUser;
    private UserEntity managerUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminUser = new UserEntity();
        adminUser.setUsername("adminUser");
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword("password");
        adminUser.setActive(true);
        adminUser.setRole(UserEntity.UserRole.ADMIN);

        auditorUser = new UserEntity();
        auditorUser.setUsername("auditorUser");
        auditorUser.setEmail("auditor@example.com");
        auditorUser.setPassword("password");
        auditorUser.setActive(true);
        auditorUser.setRole(UserEntity.UserRole.AUDITOR);

        managerUser = new UserEntity();
        managerUser.setUsername("managerUser");
        managerUser.setEmail("manager@example.com");
        managerUser.setPassword("password");
        managerUser.setActive(true);
        managerUser.setRole(UserEntity.UserRole.MANAGER);
    }

    @Test
    void testLoadUserByUsernameFoundInAdministratorRepository() {
        Administrator adminUser = new Administrator();
        adminUser.setUsername("adminUser");
        adminUser.setRole(UserEntity.UserRole.ADMIN);

        when(administratorRepository.findByUsername("adminUser")).thenReturn(Optional.of(adminUser));

        UserDetails userDetails = service.loadUserByUsername("adminUser");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomUserDetails);
        assertEquals("adminUser", userDetails.getUsername());
        verify(administratorRepository).findByUsername("adminUser");
        verifyNoInteractions(auditorRepository, managerRepository);
    }


    @Test
    void testLoadUserByUsernameFoundInAuditorRepository() {
        when(administratorRepository.findByUsername("auditorUser")).thenReturn(Optional.empty());

        Auditor auditorUser = new Auditor();
        auditorUser.setUsername("auditorUser");
        auditorUser.setRole(UserEntity.UserRole.AUDITOR);

        when(auditorRepository.findByUsername("auditorUser")).thenReturn(Optional.of(auditorUser));

        UserDetails userDetails = service.loadUserByUsername("auditorUser");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomUserDetails);
        assertEquals("auditorUser", userDetails.getUsername());
        verify(administratorRepository).findByUsername("auditorUser");
        verify(auditorRepository).findByUsername("auditorUser");
        verifyNoInteractions(managerRepository);
    }

    @Test
    void testLoadUserByUsernameFoundInManagerRepository() {
        when(administratorRepository.findByUsername("managerUser")).thenReturn(Optional.empty());
        when(auditorRepository.findByUsername("managerUser")).thenReturn(Optional.empty());

        Manager managerUser = new Manager();
        managerUser.setUsername("managerUser");
        managerUser.setRole(UserEntity.UserRole.MANAGER);

        when(managerRepository.findByUsername("managerUser")).thenReturn(Optional.of(managerUser));

        UserDetails userDetails = service.loadUserByUsername("managerUser");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof CustomUserDetails);
        assertEquals("managerUser", userDetails.getUsername());
        verify(administratorRepository).findByUsername("managerUser");
        verify(auditorRepository).findByUsername("managerUser");
        verify(managerRepository).findByUsername("managerUser");
    }


    @Test
    void testLoadUserByUsernameNotFound() {
        when(administratorRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());
        when(auditorRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());
        when(managerRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("unknownUser"));

        verify(administratorRepository).findByUsername("unknownUser");
        verify(auditorRepository).findByUsername("unknownUser");
        verify(managerRepository).findByUsername("unknownUser");
    }
}
