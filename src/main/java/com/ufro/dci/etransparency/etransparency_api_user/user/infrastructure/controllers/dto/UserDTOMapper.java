package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

public class UserDTOMapper {

    private UserDTOMapper() {
        throw new UnsupportedOperationException("Mapper class");
    }

    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        dto.setRole(user.getRole());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setNotifications(user.getNotifications());
        dto.setTotalInstitutions(user.getTotalInstitutions());
        dto.setAuditsPerformed(user.getAuditsPerformed());
        dto.setPosition(user.getPosition());
        dto.setRut(user.getRut());
        dto.setCity(user.getCity());
        dto.setColor(user.getColor());
        dto.setAcronym(user.getAcronym());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    public static User fromDto(UpdateUserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPosition(dto.getPosition());
        user.setRut(dto.getRut());
        user.setCity(dto.getCity());
        user.setColor(dto.getColor());
        user.setAcronym(dto.getAcronym());
        user.setAuditsPerformed(dto.getAuditsPerformed());
        return user;
    }

}
