package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.entities;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;

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
                List.of("Notify1", "Notify2"),
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

    @Test
    @DisplayName("Should copy notifications correctly from domain")
    void shouldCopyNotificationsFromDomain() {
        User domainUser = new User();
        domainUser.setNotifications(List.of("One", "Two"));
        UserEntity entity = UserEntity.fromDomain(domainUser);
        assertThat(entity.getNotifications()).containsExactly("One", "Two");
    }

}
