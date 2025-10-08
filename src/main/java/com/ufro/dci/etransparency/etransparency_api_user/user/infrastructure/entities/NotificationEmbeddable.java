package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.entities;

import java.time.LocalDateTime;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Notification;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEmbeddable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String message;

    private LocalDateTime date;

    public Notification toDomain() {
        Notification notification = new Notification(this.id, this.message, this.date);
        return notification;
    }

    public static NotificationEmbeddable fromDomain(Notification domain) {
        return new NotificationEmbeddable(domain.getId(), domain.getMessage(), domain.getDate());

    }

}
