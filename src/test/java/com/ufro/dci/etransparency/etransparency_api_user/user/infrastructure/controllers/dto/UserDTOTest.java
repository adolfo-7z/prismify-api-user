package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;

class UserDTOTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        UserDTO dto = new UserDTO();
        dto.setId(42L);
        dto.setUsername("juancito");
        dto.setEmail("juan@correo.cl");
        dto.setActive(true);
        dto.setRole(Role.ADMIN);
        dto.setPhoneNumber("123456789");
        dto.setNotifications(List.of("Notify1", "Notify2"));
        dto.setTotalInstitutions(10L);
        dto.setAuditsPerformed(5L);
        dto.setPosition("Director");
        dto.setRut("88888888-8");
        dto.setCity("Temuco");
        dto.setColor("Red");
        dto.setAcronym("TP");
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        assertEquals(42L, dto.getId());
        assertEquals("juancito", dto.getUsername());
        assertEquals("juan@correo.cl", dto.getEmail());
        assertTrue(dto.isActive());
        assertEquals(Role.ADMIN, dto.getRole());
        assertEquals("123456789", dto.getPhoneNumber());
        assertEquals(List.of("Notify1", "Notify2"), dto.getNotifications());
        assertEquals(10L, dto.getTotalInstitutions());
        assertEquals(5L, dto.getAuditsPerformed());
        assertEquals("Director", dto.getPosition());
        assertEquals("88888888-8", dto.getRut());
        assertEquals("Temuco", dto.getCity());
        assertEquals("Red", dto.getColor());
        assertEquals("TP", dto.getAcronym());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        List<String> notifications = List.of("Notify1", "Notify2");
        UserDTO dto = new UserDTO(
                42L,
                "juancito",
                "juan@correo.cl",
                true,
                Role.ADMIN,
                "123456789",
                notifications,
                10L,
                5L,
                "Director",
                "88888888-8",
                "Temuco",
                "Red",
                "TP",
                now,
                now);
        assertEquals(42L, dto.getId());
        assertEquals("juancito", dto.getUsername());
        assertEquals("juan@correo.cl", dto.getEmail());
        assertTrue(dto.isActive());
        assertEquals(Role.ADMIN, dto.getRole());
        assertEquals("123456789", dto.getPhoneNumber());
        assertEquals(notifications, dto.getNotifications());
        assertEquals(10L, dto.getTotalInstitutions());
        assertEquals(5L, dto.getAuditsPerformed());
        assertEquals("Director", dto.getPosition());
        assertEquals("88888888-8", dto.getRut());
        assertEquals("Temuco", dto.getCity());
        assertEquals("Red", dto.getColor());
        assertEquals("TP", dto.getAcronym());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        UserDTO dto1 = new UserDTO(
                42L, "juancito", "juan@correo.cl", true, Role.ADMIN,
                "123456789", List.of("Notify1"), 10L, 5L,
                "Director", "88888888-8", "Temuco",
                "Red", "TP", now, now);
        UserDTO dto2 = new UserDTO(
                42L, "juancito", "juan@correo.cl", true, Role.ADMIN,
                "123456789", List.of("Notify1"), 10L, 5L,
                "Director", "88888888-8", "Temuco",
                "Red", "TP", now, now);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToStringContainsFields() {
        UserDTO dto = new UserDTO();
        dto.setUsername("juancito");
        String str = dto.toString();
        assertTrue(str.contains("juancito"));
    }

}
