package com.ufro.dci.etransparency.etransparency_api_user.models.auditor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;

class AuditorTest {

    @Test
    void testAuditor() {
        Auditor auditor = new Auditor();

        auditor.setUsername("auditor_user");
        auditor.setEmail("auditor@example.com");

        auditor.setId(1L);
        auditor.setCity("City Test");
        auditor.setColor("Blue");
        auditor.setAcronym("AUD");
        auditor.setCreatedAt(new Date());
        auditor.setUpdatedAt(new Date());
        auditor.setNAuditsPerformed(5L);
        auditor.setNAssignedInstitutions(10L);
        auditor.setNotifications(new ArrayList<>(List.of("Notification 1", "Notification 2")));

        assertEquals("auditor_user", auditor.getUsername());
        assertEquals("auditor@example.com", auditor.getEmail());
        assertEquals(1L, auditor.getId());
        assertEquals("City Test", auditor.getCity());
        assertEquals("Blue", auditor.getColor());
        assertEquals("AUD", auditor.getAcronym());
        assertEquals(5L, auditor.getNAuditsPerformed());
        assertEquals(10L, auditor.getNAssignedInstitutions());
        assertEquals(2, auditor.getNotifications().size());
        assertTrue(auditor.getNotifications().contains("Notification 1"));
    }

    @Test
    void shouldAddNotificationToAuditor() {
        Auditor auditor = new Auditor();
        auditor.setNotifications(new ArrayList<>(List.of("Initial Notification")));

        assertNotNull(auditor.getNotifications());
        assertEquals(1, auditor.getNotifications().size());
        assertEquals("Initial Notification", auditor.getNotifications().get(0));

        auditor.getNotifications().add("New Notification");
        assertEquals(2, auditor.getNotifications().size());
        assertEquals("New Notification", auditor.getNotifications().get(1));
    }

    @Test
    void shouldRemoveNotificationFromAuditor() {
        Auditor auditor = new Auditor();
        auditor.setNotifications(new ArrayList<>(List.of("Notification 1", "Notification 2", "Notification 3")));

        assertEquals(3, auditor.getNotifications().size());

        auditor.getNotifications().remove("Notification 2");
        assertEquals(2, auditor.getNotifications().size());
        assertFalse(auditor.getNotifications().contains("Notification 2"));
    }

    @Test
    void shouldSetAndGetAttributesCorrectly() {
        Auditor auditor = new Auditor();

        auditor.setId(2L);
        auditor.setUsername("auditorTest");
        auditor.setEmail("auditor@test.com");
        auditor.setCity("Test City");
        auditor.setColor("Green");
        auditor.setAcronym("AUDT");

        assertEquals(2L, auditor.getId());
        assertEquals("auditorTest", auditor.getUsername());
        assertEquals("auditor@test.com", auditor.getEmail());
        assertEquals("Test City", auditor.getCity());
        assertEquals("Green", auditor.getColor());
        assertEquals("AUDT", auditor.getAcronym());
    }

    @Test
    void shouldHandleEmptyNotificationsList() {
        Auditor auditor = new Auditor();
        auditor.setNotifications(new ArrayList<>());

        assertNotNull(auditor.getNotifications());
        assertEquals(0, auditor.getNotifications().size());
    }

    @Test
    void testAllArgsConstructor() {
        List<String> notifications = new ArrayList<>(List.of("Notification 1", "Notification 2"));
        List<Institution> institutions = new ArrayList<>();

        Auditor auditor = new Auditor(1L, 10L, 5L, new Date(), new Date(), "City Test", "Red", "AUD", institutions, notifications);

        assertEquals(1L, auditor.getId());
        assertEquals("City Test", auditor.getCity());
        assertEquals("Red", auditor.getColor());
        assertEquals("AUD", auditor.getAcronym());
        assertEquals(2, auditor.getNotifications().size());
        assertTrue(auditor.getNotifications().contains("Notification 1"));
    }

    @Test
    void testBuilder() {
        List<String> notifications = new ArrayList<>(List.of("Notification 1", "Notification 2"));
        List<Institution> institutions = new ArrayList<>();

        Auditor auditor = Auditor.builder()
                .id(1L)
                .nAssignedInstitutions(10L)
                .nAuditsPerformed(5L)
                .city("Builder City")
                .color("Yellow")
                .acronym("BUD")
                .institutions(institutions)
                .notifications(notifications)
                .build();

        assertEquals(1L, auditor.getId());
        assertEquals(10L, auditor.getNAssignedInstitutions());
        assertEquals(5L, auditor.getNAuditsPerformed());
        assertEquals("Builder City", auditor.getCity());
        assertEquals("Yellow", auditor.getColor());
        assertEquals("BUD", auditor.getAcronym());
        assertEquals(2, auditor.getNotifications().size());
        assertTrue(auditor.getNotifications().contains("Notification 2"));
    }

    @Test
    void shouldAddNotificationUsingBuilderInstance() {
        Auditor auditor = Auditor.builder()
                .id(2L)
                .notifications(new ArrayList<>(List.of("Initial Notification")))
                .build();

        auditor.getNotifications().add("New Notification");
        assertEquals(2, auditor.getNotifications().size());
        assertEquals("New Notification", auditor.getNotifications().get(1));
    }
}
