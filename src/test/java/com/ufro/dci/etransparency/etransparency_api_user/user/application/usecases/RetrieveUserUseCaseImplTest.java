package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import static org.assertj.core.api.Assertions.*;

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

}
