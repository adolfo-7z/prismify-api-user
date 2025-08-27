package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`users`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 100)
    private String recoveryCode;

    private LocalDateTime recoveryCodeExpiration;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "notification")
    private List<String> notifications = new ArrayList<>();

    private Long totalInstitutions;

    private Long auditsPerformed;

    @Column(length = 100)
    private String position;

    @Column(length = 12)
    private String rut;

    @Column(length = 30)
    private String city;

    @Column(length = 20)
    private String color;

    @Column(length = 3)
    private String acronym;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User toDomain() {
        return new User(
                id,
                username,
                email,
                password,
                isActive,
                role,
                phoneNumber,
                recoveryCode,
                recoveryCodeExpiration,
                notifications != null ? List.copyOf(notifications) : new ArrayList<>(),
                totalInstitutions,
                auditsPerformed,
                position,
                rut,
                city,
                color,
                acronym,
                createdAt,
                updatedAt);
    }

    public static UserEntity fromDomain(User user) {
        if (user == null)
            return null;
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setActive(user.isActive());
        entity.setRole(user.getRole());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setRecoveryCode(user.getRecoveryCode());
        entity.setRecoveryCodeExpiration(user.getRecoveryCodeExpiration());
        entity.setNotifications(
                user.getNotifications() != null ? new ArrayList<>(user.getNotifications()) : new ArrayList<>());
        entity.setTotalInstitutions(user.getTotalInstitutions());
        entity.setAuditsPerformed(user.getAuditsPerformed());
        entity.setPosition(user.getPosition());
        entity.setRut(user.getRut());
        entity.setCity(user.getCity());
        entity.setColor(user.getColor());
        entity.setAcronym(user.getAcronym());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        return entity;
    }

}
