package com.ufro.dci.etransparency.etransparency_api_user.models;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@ToString
public class UserEntity {

    public enum UserRole {
        ADMIN, MANAGER, AUDITOR
    }

    private boolean isActive;

    @NotNull
    @Column(length = 30)
    private String username;

    @NotNull
    @Column(length = 100)
    private String email;

    @NotNull
    @Column(length = 255)
    private String password;

    @Column(length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ElementCollection
    @CollectionTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "notifications")
    private List<String> notifications;

}