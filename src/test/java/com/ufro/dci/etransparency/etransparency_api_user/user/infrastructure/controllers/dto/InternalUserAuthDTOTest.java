package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InternalUserAuthDTOTest {

    @Test
    void shouldCreateDTOWithAllFields() {
        InternalUserAuthDTO dto = new InternalUserAuthDTO(42L, "juancito", "transparencia123", "ADMIN", true);
        assertEquals(42L, dto.getId());
        assertEquals("juancito", dto.getUsername());
        assertEquals("transparencia123", dto.getPassword());
        assertEquals("ADMIN", dto.getRole());
        assertTrue(dto.isActive());
    }

    @Test
    void shouldSetAndGetFieldsProperly() {
        InternalUserAuthDTO dto = new InternalUserAuthDTO();
        dto.setId(101L);
        dto.setUsername("juancito");
        dto.setPassword("transparencia123");
        dto.setRole("ADMIN");
        dto.setActive(false);
        assertEquals(101L, dto.getId());
        assertEquals("juancito", dto.getUsername());
        assertEquals("transparencia123", dto.getPassword());
        assertEquals("ADMIN", dto.getRole());
        assertFalse(dto.isActive());
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        InternalUserAuthDTO dto = new InternalUserAuthDTO();
        assertNotNull(dto);
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        InternalUserAuthDTO dto1 = new InternalUserAuthDTO(1L, "user", "pass", "ROLE", true);
        InternalUserAuthDTO dto2 = new InternalUserAuthDTO(1L, "user", "pass", "ROLE", true);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void shouldHaveReadableToString() {
        InternalUserAuthDTO dto = new InternalUserAuthDTO(7L, "juancito", "nopeek", "ADMIN", true);
        String result = dto.toString();
        assertTrue(result.contains("juancito"));
        assertTrue(result.contains("ADMIN"));
    }

}
