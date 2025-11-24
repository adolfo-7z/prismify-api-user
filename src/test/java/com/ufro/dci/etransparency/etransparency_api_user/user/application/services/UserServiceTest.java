package com.ufro.dci.etransparency.etransparency_api_user.user.application.services;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.DeleteUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.RetrieveUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.UpdateUserUseCase;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private RetrieveUserUseCase retrieveUserUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldDelegateCreateUser() {
        User user = new User();
        User savedUser = new User();
        Mockito.when(createUserUseCase.createUser(user)).thenReturn(savedUser);
        User result = userService.createUser(user);
        assertThat(result).isSameAs(savedUser);
        Mockito.verify(createUserUseCase).createUser(user);
    }

    @Test
    void shouldDelegateGetUserById() {
        User user = new User();
        Mockito.when(retrieveUserUseCase.getUserById(1L)).thenReturn(user);
        User result = userService.getUserById(1L);
        assertThat(result).isSameAs(user);
        Mockito.verify(retrieveUserUseCase).getUserById(1L);
    }

    @Test
    void shouldDelegateGetUserByUsername() {
        User user = new User();
        Mockito.when(retrieveUserUseCase.getUserByUsername("juancito")).thenReturn(user);
        User result = userService.getUserByUsername("juancito");
        assertThat(result).isSameAs(user);
        Mockito.verify(retrieveUserUseCase).getUserByUsername("juancito");
    }

    @Test
    void shouldDelegateUpdateUser() {
        User update = new User();
        User updated = new User();
        Mockito.when(updateUserUseCase.updateUser(42L, update)).thenReturn(updated);
        User result = userService.updateUser(42L, update);
        assertThat(result).isSameAs(updated);
        Mockito.verify(updateUserUseCase).updateUser(42L, update);
    }

    @Test
    void shouldDelegateToggleUserStatus() {
        Mockito.when(updateUserUseCase.toggleUserStatus(7L)).thenReturn("User activated");
        String result = userService.toggleUserStatus(7L);
        assertThat(result).isEqualTo("User activated");
        Mockito.verify(updateUserUseCase).toggleUserStatus(7L);
    }

    @Test
    void shouldDelegateIncrementAuditsPerformed() {
        userService.incrementAuditsPerformed(9L);
        Mockito.verify(updateUserUseCase).incrementAuditsPerformed(9L);
    }

    @Test
    void shouldDelegateDeleteUser() {
        Mockito.when(deleteUserUseCase.deleteUser(66L)).thenReturn("User deleted");
        String result = userService.deleteUser(66L);
        assertThat(result).isEqualTo("User deleted");
        Mockito.verify(deleteUserUseCase).deleteUser(66L);
    }

}
