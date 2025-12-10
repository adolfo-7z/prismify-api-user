package prismify.user.infrastructure.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;
import prismify.user.domain.models.Role;
import prismify.user.domain.models.User;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("date ASC")
    private List<NotificationEntity> notifications = new ArrayList<>();

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
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setActive(this.isActive);
        user.setRole(this.role);
        user.setPhoneNumber(this.phoneNumber);
        user.setRecoveryCode(this.recoveryCode);
        user.setRecoveryCodeExpiration(this.recoveryCodeExpiration);
        user.setNotifications(
                this.notifications != null
                        ? this.notifications.stream()
                                .map(NotificationEntity::toDomain)
                                .collect(Collectors.toCollection(ArrayList::new))
                        : new ArrayList<>());
        user.setTotalInstitutions(this.totalInstitutions);
        user.setAuditsPerformed(this.auditsPerformed);
        user.setPosition(this.position);
        user.setRut(this.rut);
        user.setCity(this.city);
        user.setColor(this.color);
        user.setAcronym(this.acronym);
        user.setCreatedAt(this.createdAt);
        user.setUpdatedAt(this.updatedAt);
        return user;
    }

    public static UserEntity fromDomain(User domain) {
        if (domain == null)
            return null;
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setActive(domain.isActive());
        entity.setRole(domain.getRole());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setRecoveryCode(domain.getRecoveryCode());
        entity.setRecoveryCodeExpiration(domain.getRecoveryCodeExpiration());
        entity.setNotifications(
                domain.getNotifications() != null
                        ? domain.getNotifications().stream()
                                .map(n -> NotificationEntity.fromDomain(n, entity))
                                .toList()
                        : List.of());
        entity.setTotalInstitutions(domain.getTotalInstitutions());
        entity.setAuditsPerformed(domain.getAuditsPerformed());
        entity.setPosition(domain.getPosition());
        entity.setRut(domain.getRut());
        entity.setCity(domain.getCity());
        entity.setColor(domain.getColor());
        entity.setAcronym(domain.getAcronym());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

}
