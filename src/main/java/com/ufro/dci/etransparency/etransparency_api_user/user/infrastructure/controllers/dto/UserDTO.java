package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private boolean isActive;
    private Role role;
    private String phoneNumber;
    private List<NotificationDTO> notifications;
    private Long totalInstitutions;
    private Long auditsPerformed;
    private String position;
    private String rut;
    private String city;
    private String color;
    private String acronym;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
