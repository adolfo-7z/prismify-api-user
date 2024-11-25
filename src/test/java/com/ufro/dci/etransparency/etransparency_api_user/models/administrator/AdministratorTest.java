package com.ufro.dci.etransparency.etransparency_api_user.models.administrator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class AdministratorTest {

    @Test
    void testAdministrator() {
        // Crea una instancia de Administrator sin usar el builder
        Administrator admin = new Administrator();

        // Establece valores usando los setters
        admin.setUsername("admin_user");
        admin.setEmail("admin@example.com");

        // Establece el ID y una lista mutable de notificaciones
        admin.setId(1L);
        admin.setNotifications(new ArrayList<>(List.of("Notification 1", "Notification 2")));

        // Verifica que los valores se hayan establecido correctamente
        assertEquals("admin_user", admin.getUsername());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals(1L, admin.getId());
        assertEquals(2, admin.getNotifications().size());
        assertTrue(admin.getNotifications().contains("Notification 1"));
    }

    @Test
    void shouldAddNotificationToAdministrator() {
        Administrator admin = new Administrator();
        admin.setNotifications(new ArrayList<>(List.of("Initial Notification")));

        assertNotNull(admin.getNotifications());
        assertEquals(1, admin.getNotifications().size());
        assertEquals("Initial Notification", admin.getNotifications().get(0));

        // Agrega una notificación
        admin.getNotifications().add("New Notification");
        assertEquals(2, admin.getNotifications().size());
        assertEquals("New Notification", admin.getNotifications().get(1));
    }

    @Test
    void shouldRemoveNotificationFromAdministrator() {
        Administrator admin = new Administrator();
        admin.setNotifications(new ArrayList<>(List.of("Notification 1", "Notification 2", "Notification 3")));

        assertEquals(3, admin.getNotifications().size());

        // Elimina una notificación
        admin.getNotifications().remove("Notification 2");
        assertEquals(2, admin.getNotifications().size());
        assertFalse(admin.getNotifications().contains("Notification 2"));
    }

    @Test
    void shouldSetAndGetAttributesCorrectly() {
        Administrator admin = new Administrator();

        admin.setId(2L);
        admin.setUsername("adminTest");
        admin.setEmail("admin@test.com");

        assertEquals(2L, admin.getId());
        assertEquals("adminTest", admin.getUsername());
        assertEquals("admin@test.com", admin.getEmail());
    }

    @Test
    void shouldHandleEmptyNotificationsList() {
        Administrator admin = new Administrator();
        admin.setNotifications(new ArrayList<>()); // Usar una lista mutable vacía

        assertNotNull(admin.getNotifications());
        assertEquals(0, admin.getNotifications().size());
    }

    @Test
    void testAllArgsConstructor() {
        // Crea una lista mutable de notificaciones
        List<String> notifications = new ArrayList<>(List.of("Notification 1", "Notification 2"));

        // Crea una instancia usando el constructor de todos los argumentos
        Administrator admin = new Administrator(1L,  notifications);

        // Verifica que los valores se hayan establecido correctamente
        assertEquals(1L, admin.getId());
        assertEquals(2, admin.getNotifications().size());
        assertTrue(admin.getNotifications().contains("Notification 1"));
    }

    @Test
    void testBuilder() {
        // Crea una lista mutable de notificaciones
        List<String> notifications = new ArrayList<>(List.of("Notification 1", "Notification 2"));

        // Usa el builder para crear una instancia de Administrator
        Administrator admin = Administrator.builder()
                .id(1L)
                .notifications(notifications)
                .build();

        // Verifica que los valores se hayan establecido correctamente
        assertEquals(1L, admin.getId());
        assertEquals(2, admin.getNotifications().size());
        assertTrue(admin.getNotifications().contains("Notification 2"));
    }

    @Test
    void shouldAddNotificationUsingBuilderInstance() {
        // Usa el builder y verifica si la lista es mutable
        Administrator admin = Administrator.builder()
                .id(2L)
                .notifications(new ArrayList<>(List.of("Initial Notification")))
                .build();

        // Agrega una nueva notificación
        admin.getNotifications().add("New Notification");
        assertEquals(2, admin.getNotifications().size());
        assertEquals("New Notification", admin.getNotifications().get(1));
    }
}
