package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.Role;
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
        void shouldReturnUserByEmail() {
                String email = "prueba@correo.cl";
                User expectedUser = new User();
                expectedUser.setEmail(email);
                expectedUser.setUsername("pruebaUser");
                Mockito.when(userRepository.findByEmail(email)).thenReturn(expectedUser);
                User result = useCase.getUserByEmail(email);
                assertThat(result)
                                .isNotNull()
                                .extracting(User::getEmail, User::getUsername)
                                .containsExactly("prueba@correo.cl", "pruebaUser");
                Mockito.verify(userRepository).findByEmail(email);
        }

        @Test
        void shouldReturnPagedUsersWithFilters() {
                int page = 0;
                int size = 10;
                String username = "prueba";
                String email = "prueba@correo.cl";
                String date = "2025-01-01";
                Boolean active = true;
                Role role = Role.ADMIN;
                User user1 = new User();
                user1.setId(1L);
                user1.setUsername("prueba");
                user1.setEmail("prueba@correo.cl");
                Page<User> expectedPage = new PageImpl<>(List.of(user1));
                Mockito.when(userRepository.findAll(page, size, username, email, date, active, role))
                                .thenReturn(expectedPage);
                Page<User> result = useCase.getAllUsers(page, size, username, email, date, active, role);
                assertThat(result).isNotNull();
                assertThat(result.getContent()).hasSize(1);
                assertThat(result.getContent().get(0))
                                .extracting(User::getId, User::getUsername, User::getEmail)
                                .containsExactly(1L, "prueba", "prueba@correo.cl");
                Mockito.verify(userRepository)
                                .findAll(page, size, username, email, date, active, role);
        }

}
