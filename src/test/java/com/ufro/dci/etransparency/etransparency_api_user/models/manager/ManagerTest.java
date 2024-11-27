package com.ufro.dci.etransparency.etransparency_api_user.models.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;

class ManagerTest {

    @Test
    void testManager() {
        Manager manager = new Manager();

        manager.setUsername("manager_user");
        manager.setEmail("manager@example.com");

        manager.setId(1L);
        manager.setPosition("General Manager");
        manager.setRut("12345678-9");
        manager.setNotifications(new ArrayList<>(List.of("Notification 1", "Notification 2")));

        assertEquals("manager_user", manager.getUsername());
        assertEquals("manager@example.com", manager.getEmail());
        assertEquals(1L, manager.getId());
        assertEquals("General Manager", manager.getPosition());
        assertEquals("12345678-9", manager.getRut());
        assertEquals(2, manager.getNotifications().size());
        assertTrue(manager.getNotifications().contains("Notification 1"));
    }

    @Test
    void shouldAddNotificationToManager() {
        Manager manager = new Manager();
        manager.setNotifications(new ArrayList<>(List.of("Initial Notification")));

        assertNotNull(manager.getNotifications());
        assertEquals(1, manager.getNotifications().size());
        assertEquals("Initial Notification", manager.getNotifications().get(0));

        manager.getNotifications().add("New Notification");
        assertEquals(2, manager.getNotifications().size());
        assertEquals("New Notification", manager.getNotifications().get(1));
    }

    @Test
    void shouldRemoveNotificationFromManager() {
        Manager manager = new Manager();
        manager.setNotifications(new ArrayList<>(List.of("Notification 1", "Notification 2", "Notification 3")));

        assertEquals(3, manager.getNotifications().size());

        manager.getNotifications().remove("Notification 2");
        assertEquals(2, manager.getNotifications().size());
        assertFalse(manager.getNotifications().contains("Notification 2"));
    }

    @Test
    void shouldSetAndGetAttributesCorrectly() {
        Manager manager = new Manager();

        manager.setId(2L);
        manager.setUsername("managerTest");
        manager.setEmail("manager@test.com");
        manager.setPosition("Operations Manager");
        manager.setRut("87654321-0");

        assertEquals(2L, manager.getId());
        assertEquals("managerTest", manager.getUsername());
        assertEquals("manager@test.com", manager.getEmail());
        assertEquals("Operations Manager", manager.getPosition());
        assertEquals("87654321-0", manager.getRut());
    }

    @Test
    void shouldHandleEmptyNotificationsList() {
        Manager manager = new Manager();
        manager.setNotifications(new ArrayList<>()); // Usar una lista mutable vacía

        assertNotNull(manager.getNotifications());
        assertEquals(0, manager.getNotifications().size());
    }

    @Test
    void testAllArgsConstructor() {
        List<String> notifications = new ArrayList<>(List.of("Notification 1", "Notification 2"));

        Manager manager = new Manager(1L, "General Manager", "12345678-9", null, notifications);

        assertEquals(1L, manager.getId());
        assertEquals("General Manager", manager.getPosition());
        assertEquals("12345678-9", manager.getRut());
        assertEquals(2, manager.getNotifications().size());
        assertTrue(manager.getNotifications().contains("Notification 1"));
    }

    @Test
    void testBuilder() {
        List<String> notifications = new ArrayList<>(List.of("Notification 1", "Notification 2"));
        Institution institution = new Institution();

        Manager manager = Manager.builder()
                .id(1L)
                .position("Financial Manager")
                .rut("11223344-5")
                .institution(institution)
                .notifications(notifications)
                .build();

        assertEquals(1L, manager.getId());
        assertEquals("Financial Manager", manager.getPosition());
        assertEquals("11223344-5", manager.getRut());
        assertEquals(2, manager.getNotifications().size());
        assertTrue(manager.getNotifications().contains("Notification 2"));
    }

    @Test
    void shouldAddNotificationUsingBuilderInstance() {
        Manager manager = Manager.builder()
                .id(2L)
                .notifications(new ArrayList<>(List.of("Initial Notification")))
                .build();

        manager.getNotifications().add("New Notification");
        assertEquals(2, manager.getNotifications().size());
        assertEquals("New Notification", manager.getNotifications().get(1));
    }
}
