package prismify.user.infrastructure.entities;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.*;

import prismify.user.domain.models.*;

@Tag("UserEntityTests")
@DisplayName("Unit tests for UserEntity conversion")
class UserEntityTest {

    @Test
    @DisplayName("Should convert from domain to entity and back without data loss")
    void shouldConvertFromDomainAndBack() {
        User domainUser = new User(
                42L,
                "juancito",
                "juan@correo.cl",
                "hashedPassword123",
                true,
                Role.ADMIN,
                "123456789",
                "RECOVERY-CODE-001",
                LocalDateTime.of(9999, 12, 31, 23, 59),
                List.of(new Notification(1L, "Notification 1", LocalDateTime.now()),
                        new Notification(1L, "Notification 1", LocalDateTime.now())),
                5L,
                3L,
                "Magos Prime",
                "13.456.789-0",
                "Temuco",
                "Red",
                "ADPT",
                LocalDateTime.of(2020, 1, 1, 12, 0),
                LocalDateTime.of(2025, 7, 25, 15, 45));
        UserEntity entity = UserEntity.fromDomain(domainUser);
        User converted = entity.toDomain();
        assertThat(converted).usingRecursiveComparison().isEqualTo(domainUser);
    }

    @Test
    @DisplayName("fromDomain should return null when given null")
    void fromDomain_shouldReturnNull_whenInputIsNull() {
        UserEntity result = UserEntity.fromDomain(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should initialize notifications to empty list when null in domain")
    void shouldInitializeNotificationsToEmptyList_whenDomainHasNullNotifications() {
        User domainUser = new User();
        domainUser.setNotifications(null);
        UserEntity entity = UserEntity.fromDomain(domainUser);
        assertThat(entity.getNotifications()).isNotNull();
        assertThat(entity.getNotifications()).isEmpty();
    }

}
