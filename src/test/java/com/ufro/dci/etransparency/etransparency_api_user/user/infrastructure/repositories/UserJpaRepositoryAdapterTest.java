package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.entities.UserEntity;

@ExtendWith(MockitoExtension.class)
class UserJpaRepositoryAdapterTest {

    @Mock
    private UserJpaRepository jpaRepository;

    @InjectMocks
    private UserJpaRepositoryAdapter adapter;

    private static User sampleUser = new User(
            1L,
            "juancito",
            "juan@correo.cl",
            "transparencia123",
            true,
            Role.ADMIN,
            "88888888-8",
            null,
            null,
            new ArrayList<>(),
            0L,
            0L,
            "Tester",
            "88888888-8",
            "Temuco",
            "Rojo",
            "TSR",
            LocalDateTime.now(),
            LocalDateTime.now());

    private final UserEntity sampleEntity = UserEntity.fromDomain(sampleUser);

    @Test
    void shouldSaveAndFindUserById() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(sampleEntity);
        when(jpaRepository.findById(anyLong())).thenReturn(Optional.of(sampleEntity));
        User saved = adapter.save(sampleUser);
        User found = adapter.findById(saved.getId());
        assertThat(found.getUsername()).isEqualTo("juancito");
        assertThat(found.getEmail()).isEqualTo("juan@correo.cl");
    }

    @Test
    void shouldFindUserByUsername() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(sampleEntity);
        when(jpaRepository.findByUsername("juancito")).thenReturn(Optional.of(sampleEntity));
        adapter.save(sampleUser);
        User found = adapter.findByUsername("juancito");
        assertThat(found.getEmail()).isEqualTo("juan@correo.cl");
    }

    @Test
    void shouldReturnTrueIfEmailExists() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(sampleEntity);
        when(jpaRepository.existsByEmail("juan@correo.cl")).thenReturn(true);
        adapter.save(sampleUser);
        boolean exists = adapter.existsByEmail("juan@correo.cl");
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnTrueIfRoleExists() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(sampleEntity);
        when(jpaRepository.existsByRole(Role.ADMIN)).thenReturn(true);
        adapter.save(sampleUser);
        boolean exists = adapter.existsByRole(Role.ADMIN);
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnAllUsers() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(sampleEntity);
        when(jpaRepository.findAll()).thenReturn(List.of(sampleEntity));
        adapter.save(sampleUser);
        List<User> users = adapter.findAll();
        assertThat(users).hasSize(1);
    }

    @Test
    void shouldUpdateUser() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(sampleEntity);
        when(jpaRepository.findById(anyLong())).thenReturn(Optional.of(sampleEntity));
        sampleEntity.setNotifications(new ArrayList<>());
        User saved = adapter.save(sampleUser);
        saved.setEmail("juan@correo.cl");
        Optional<User> updated = adapter.update(saved);
        assertThat(updated).isPresent();
        assertThat(updated.get().getEmail()).isEqualTo("juan@correo.cl");
    }

    @Test
    void shouldDeleteUserById() {
        when(jpaRepository.save(any(UserEntity.class))).thenReturn(sampleEntity);
        when(jpaRepository.existsById(anyLong())).thenReturn(true).thenReturn(false);
        User saved = adapter.save(sampleUser);
        boolean deleted = adapter.deleteById(saved.getId());
        assertThat(deleted).isTrue();
        assertThat(jpaRepository.existsById(saved.getId())).isFalse();
    }

    @Test
    void shouldThrowIfUserNotFoundById() {
        assertThatThrownBy(() -> adapter.findById(999L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldThrowIfUserNotFoundByUsername() {
        assertThatThrownBy(() -> adapter.findByUsername("nonexistent"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldFindUserByEmail() {
        when(jpaRepository.findByEmail("juan@correo.cl"))
                .thenReturn(Optional.of(sampleEntity));
        User found = adapter.findByEmail("juan@correo.cl");
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("juancito");
    }

    @Test
    void shouldThrowIfUserNotFoundByEmail() {
        when(jpaRepository.findByEmail("missing@mail.cl"))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> adapter.findByEmail("missing@mail.cl"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldFindUserByRole() {
        when(jpaRepository.findByRole(Role.ADMIN))
                .thenReturn(Optional.of(sampleEntity));
        User found = adapter.findByRole(Role.ADMIN);
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("juan@correo.cl");
    }

    @Test
    void shouldThrowIfUserNotFoundByRole() {
        when(jpaRepository.findByRole(Role.AUDITOR))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> adapter.findByRole(Role.AUDITOR))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldReturnTrueIfUsernameExists() {
        when(jpaRepository.existsByUsername("juancito")).thenReturn(true);
        boolean exists = adapter.existsByUsername("juancito");
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseIfUsernameDoesNotExist() {
        when(jpaRepository.existsByUsername("missing")).thenReturn(false);
        boolean exists = adapter.existsByUsername("missing");
        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnPagedFilteredUsers() {
        Object[] row = new Object[] {
                1L,
                "juancito",
                "juan@correo.cl",
                true,
                "ADMIN",
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                Timestamp.valueOf(LocalDateTime.now()),
                3L,
                10L
        };
        Page<Object[]> mockedPage = new PageImpl<>(List.<Object[]>of(row));
        when(jpaRepository.findAllUsersFiltered(
                eq("juancito"),
                eq("juan@correo.cl"),
                eq(true),
                eq("ADMIN"),
                eq("2024-01-01"),
                any(Pageable.class))).thenReturn(mockedPage);
        Page<User> result = adapter.findAll(
                0, 10,
                "juancito",
                "juan@correo.cl",
                "2024-01-01",
                true,
                Role.ADMIN);
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        User u = result.getContent().get(0);
        assertThat(u.getUsername()).isEqualTo("juancito");
        assertThat(u.getRole()).isEqualTo(Role.ADMIN);
        assertThat(u.getTotalInstitutions()).isEqualTo(3L);
        assertThat(u.getAuditsPerformed()).isEqualTo(10L);
    }

}
