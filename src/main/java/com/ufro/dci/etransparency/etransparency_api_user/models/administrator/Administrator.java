package com.ufro.dci.etransparency.etransparency_api_user.models.administrator;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_administrator_username", columnList = "username"),
        @Index(name = "idx_administrator_email", columnList = "email")
})
public class Administrator extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "administrator_notifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "notifications")
    private List<String> notifications;
    
}
