package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.entities;

import java.time.LocalDateTime;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Notification;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String message;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Notification toDomain() {
        return new Notification(this.id, this.message, this.date);
    }

    public static NotificationEntity fromDomain(Notification domain, UserEntity user) {
        NotificationEntity entity = new NotificationEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setId(domain.getId());
        entity.setMessage(domain.getMessage());
        entity.setDate(domain.getDate());
        entity.setUser(user);
        return entity;
    }

}
