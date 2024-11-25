package com.ufro.dci.etransparency.etransparency_api_user.services.notification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.ResourceNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
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

class NotificationServiceImplTest {

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private AuditorRepository auditorRepository;

    @InjectMocks
    private NotificationServiceImpl service;

    private Administrator admin;
    private Auditor auditor;
    private Manager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        admin = new Administrator();
        admin.setId(1L);
        admin.setNotifications(List.of("Admin Notification 1", "Admin Notification 2"));

        auditor = new Auditor();
        auditor.setId(2L);
        auditor.setNotifications(List.of("Auditor Notification 1"));

        manager = new Manager();
        manager.setId(3L);
        manager.setNotifications(List.of("Manager Notification 1", "Manager Notification 2", "Manager Notification 3"));
    }

    @Test
    void testGetUserNotificationsForAdministrator() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.of(admin));

        var result = service.getUserNotifications(1L, UserRole.ADMIN);

        assertNotNull(result);
        assertTrue(result instanceof java.util.Map);
        List<String> notifications = (List<String>) ((Map<?, ?>) result).get("notifications");
        assertEquals(2, notifications.size());
        assertEquals("Admin Notification 1", notifications.get(0));
        verify(administratorRepository).findById(1L);
        verifyNoInteractions(auditorRepository, managerRepository);
    }

    @Test
    void testGetUserNotificationsForAuditor() {
        when(auditorRepository.findById(2L)).thenReturn(Optional.of(auditor));

        var result = service.getUserNotifications(2L, UserRole.AUDITOR);

        assertNotNull(result);
        assertTrue(result instanceof java.util.Map);
        List<String> notifications = (List<String>) ((Map<?, ?>) result).get("notifications");
        assertEquals(1, notifications.size());
        assertEquals("Auditor Notification 1", notifications.get(0));
        verify(auditorRepository).findById(2L);
        verifyNoInteractions(administratorRepository, managerRepository);
    }

    @Test
    void testGetUserNotificationsForManager() {
        when(managerRepository.findById(3L)).thenReturn(Optional.of(manager));

        var result = service.getUserNotifications(3L, UserRole.MANAGER);

        assertNotNull(result);
        assertTrue(result instanceof java.util.Map);
        List<String> notifications = (List<String>) ((Map<?, ?>) result).get("notifications");
        assertEquals(3, notifications.size());
        assertEquals("Manager Notification 1", notifications.get(0));
        verify(managerRepository).findById(3L);
        verifyNoInteractions(administratorRepository, auditorRepository);
    }

    @Test
    void testGetUserNotificationsForAdministratorNotFound() {
        when(administratorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getUserNotifications(1L, UserRole.ADMIN));
        verify(administratorRepository).findById(1L);
        verifyNoInteractions(auditorRepository, managerRepository);
    }

    @Test
    void testGetUserNotificationsForInvalidRole() {
        assertThrows(NullPointerException.class, () -> service.getUserNotifications(1L, null));
        verifyNoInteractions(administratorRepository, auditorRepository, managerRepository);
    }
}
