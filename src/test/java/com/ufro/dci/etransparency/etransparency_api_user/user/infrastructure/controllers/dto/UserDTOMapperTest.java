package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.user.UpdateUserRequestDTO;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.user.UserDTO;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.user.UserDTOMapper;

@ExtendWith(MockitoExtension.class)
class UserDTOMapperTest {

    @Test
    void testToDto_ShouldMapAllFieldsCorrectly() {
        User user = new User();
        user.setId(42L);
        user.setUsername("juancito");
        user.setEmail("juan@correo.cl");
        user.setActive(true);
        user.setRole(Role.ADMIN);
        user.setPhoneNumber("123456789");
        user.setNotifications(new ArrayList<>());
        user.setTotalInstitutions(5L);
        user.setAuditsPerformed(12L);
        user.setPosition("Director");
        user.setRut("12.345.678-9");
        user.setCity("Temuco");
        user.setColor("Red");
        user.setAcronym("AM");
        user.setCreatedAt(LocalDateTime.now().minusDays(10));
        user.setUpdatedAt(LocalDateTime.now());
        UserDTO dto = UserDTOMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.isActive(), dto.isActive());
        assertEquals(user.getRole(), dto.getRole());
        assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(user.getNotifications(), dto.getNotifications());
        assertEquals(user.getTotalInstitutions(), dto.getTotalInstitutions());
        assertEquals(user.getAuditsPerformed(), dto.getAuditsPerformed());
        assertEquals(user.getPosition(), dto.getPosition());
        assertEquals(user.getRut(), dto.getRut());
        assertEquals(user.getCity(), dto.getCity());
        assertEquals(user.getColor(), dto.getColor());
        assertEquals(user.getAcronym(), dto.getAcronym());
        assertEquals(user.getCreatedAt(), dto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), dto.getUpdatedAt());
    }

    @Test
    void testFromDto_ShouldMapAllFieldsCorrectly() {
        UpdateUserRequestDTO dto = new UpdateUserRequestDTO();
        dto.setUsername("juancito");
        dto.setEmail("juan@correo.cl");
        dto.setPassword("transparencia123");
        dto.setPhoneNumber("987654321");
        dto.setPosition("Dominus");
        dto.setRut("99.999.999-9");
        dto.setCity("temuco");
        dto.setColor("Crimson");
        dto.setAcronym("DM");
        dto.setAuditsPerformed(8L);
        User user = UserDTOMapper.fromDto(dto);
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getPassword(), user.getPassword());
        assertEquals(dto.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(dto.getPosition(), user.getPosition());
        assertEquals(dto.getRut(), user.getRut());
        assertEquals(dto.getCity(), user.getCity());
        assertEquals(dto.getColor(), user.getColor());
        assertEquals(dto.getAcronym(), user.getAcronym());
        assertEquals(dto.getAuditsPerformed(), user.getAuditsPerformed());
    }

}
