package prismify.user.domain.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isActive;
    private Role role;
    private String phoneNumber;
    private String recoveryCode;
    private LocalDateTime recoveryCodeExpiration;
    private List<Notification> notifications;

    private Long totalInstitutions;
    private Long auditsPerformed;

    private String position;
    private String rut;
    private String city;
    private String color;
    private String acronym;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isAuditor() {
        return this.role == Role.AUDITOR;
    }

    public boolean isManager() {
        return this.role == Role.MANAGER;
    }

    public boolean isAdministrator() {
        return this.role == Role.ADMIN;
    }
    
}
