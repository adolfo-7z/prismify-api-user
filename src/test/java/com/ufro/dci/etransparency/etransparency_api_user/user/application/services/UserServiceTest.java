package com.ufro.dci.etransparency.etransparency_api_user.user.application.services;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Notification;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.DeleteUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.ManageNotificationUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.PasswordRecoveryUseCase;
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

    @Mock
    private PasswordRecoveryUseCase passwordRecoveryUseCase;

    @Mock
    private ManageNotificationUseCase manageNotificationUseCase;

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
    void shouldDelegateGetUserByEmail() {
        User user = new User();
        Mockito.when(retrieveUserUseCase.getUserByEmail("test@example.com")).thenReturn(user);

        User result = userService.getUserByEmail("test@example.com");

        assertThat(result).isSameAs(user);
        Mockito.verify(retrieveUserUseCase).getUserByEmail("test@example.com");
    }

    @Test
    void shouldDelegateGetAllUsers() {
        @SuppressWarnings("unchecked")
        Page<User> page = Mockito.mock(Page.class);
        Mockito.when(retrieveUserUseCase.getAllUsers(0, 10, "abc", "mail", "date", true, Role.ADMIN))
                .thenReturn(page);
        Page<User> result = userService.getAllUsers(0, 10, "abc", "mail", "date", true, Role.ADMIN);
        assertThat(result).isSameAs(page);
        Mockito.verify(retrieveUserUseCase)
                .getAllUsers(0, 10, "abc", "mail", "date", true, Role.ADMIN);
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

    @Test
    void shouldDelegateSendRecoveryCode() {
        userService.sendRecoveryCode("correo@prueba.com");
        Mockito.verify(passwordRecoveryUseCase).sendRecoveryCode("correo@prueba.com");
    }

    @Test
    void shouldDelegateValidateRecoveryCode() {
        userService.validateRecoveryCode("correo@prueba.com", "1234");
        Mockito.verify(passwordRecoveryUseCase).validateRecoveryCode("correo@prueba.com", "1234");
    }

    @Test
    void shouldDelegateValidateNewPassword() {
        userService.validateNewPassword("correo@prueba.com", "pwd", "pwd");
        Mockito.verify(passwordRecoveryUseCase)
                .validateNewPassword("correo@prueba.com", "pwd", "pwd");
    }

    @Test
    void shouldDelegateCreateNotification() {
        userService.createNotification(10L, "Hello World");
        Mockito.verify(manageNotificationUseCase).createNotification(10L, "Hello World");
    }

    @Test
    void shouldDelegateGetNotifications() {
        List<Notification> notifications = List.of(new Notification());
        Mockito.when(manageNotificationUseCase.getNotifications(10L)).thenReturn(notifications);
        List<Notification> result = userService.getNotifications(10L);
        assertThat(result).isSameAs(notifications);
        Mockito.verify(manageNotificationUseCase).getNotifications(10L);
    }

    @Test
    void shouldDelegateRemoveNotification() {
        userService.removeNotification(10L, 5L);
        Mockito.verify(manageNotificationUseCase).removeNotification(10L, 5L);
    }

    @Test
    void shouldDelegateClearNotifications() {
        userService.clearNotifications(10L);
        Mockito.verify(manageNotificationUseCase).clearNotifications(10L);
    }

    @Test
    void shouldDelegateCreateAdminNotification() {
        userService.createAdminNotification("System alert");
        Mockito.verify(manageNotificationUseCase).createAdminNotification("System alert");
    }

}
