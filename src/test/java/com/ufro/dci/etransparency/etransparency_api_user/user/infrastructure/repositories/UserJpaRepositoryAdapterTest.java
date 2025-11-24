package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
            "High Magos",
            "88888888-8",
            "Forge World Mars",
            "Martian Red",
            "Sigma",
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

}
