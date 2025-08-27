package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class RetrieveUserUseCaseImplTest {

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private RetrieveUserUseCaseImpl useCase;

        @Test
        void shouldReturnUserById() {
                Long userId = 101L;
                User expectedUser = new User();
                expectedUser.setId(userId);
                expectedUser.setUsername("prueba");
                Mockito.when(userRepository.findById(userId)).thenReturn(expectedUser);
                User result = useCase.getUserById(userId);
                assertThat(result)
                                .isNotNull()
                                .extracting(User::getId, User::getUsername)
                                .containsExactly(101L, "prueba");
                Mockito.verify(userRepository).findById(userId);
        }

        @Test
        void shouldReturnUserByUsername() {
                String username = "prueba";
                User expectedUser = new User();
                expectedUser.setUsername(username);
                expectedUser.setEmail("prueba@correo.cl");
                Mockito.when(userRepository.findByUsername(username)).thenReturn(expectedUser);
                User result = useCase.getUserByUsername(username);
                assertThat(result)
                                .isNotNull()
                                .extracting(User::getUsername, User::getEmail)
                                .containsExactly("prueba", "prueba@correo.cl");
                Mockito.verify(userRepository).findByUsername(username);
        }

        @Test
        void shouldReturnPagedUserList() {
                int page = 0;
                int size = 10;
                String order = "asc";
                String name = "Tech";
                List<User> users = List.of(
                                new User(null, "tech1", "tech1@correo.com", name, false, null, name, name, null, null,
                                                null, null, name,
                                                name, name, name, name, null, null),
                                new User(null, "tech2", "tech2@correo.com", name, false, null, name, name, null, null,
                                                null, null, name,
                                                name, name, name, name, null, null));
                Mockito.when(userRepository.findAllPaged(page, size, order, name)).thenReturn(users);
                List<User> result = useCase.getAllUsers(page, size, order, name);
                assertThat(result)
                                .hasSize(2)
                                .extracting(User::getUsername)
                                .containsExactly("tech1", "tech2");
                Mockito.verify(userRepository).findAllPaged(page, size, order, name);
        }

        @Test
        void shouldReturnUserNotifications() {
                Long userId = 101L;
                List<String> notifications = List.of("Notification 1", "Notification 2");
                User user = new User();
                user.setId(userId);
                user.setNotifications(notifications);
                Mockito.when(userRepository.findById(userId)).thenReturn(user);
                List<String> result = useCase.getUserNotifications(userId);
                assertThat(result)
                                .isNotNull()
                                .hasSize(2)
                                .containsExactly("Notification 1", "Notification 2");
                Mockito.verify(userRepository).findById(userId);
        }

}
