package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.PasswordHasher;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher hasher;

    @InjectMocks
    private CreateUserUseCaseImpl useCase;

    @Test
    void shouldCreateUserWithDefaultsAndHashedPassword() {
        User inputUser = new User();
        inputUser.setUsername("juancito");
        inputUser.setEmail("juan@correo.cl");
        inputUser.setPassword("contraseña");
        String hashedPassword = "hashedContraseña123";
        Mockito.when(hasher.hash("contraseña")).thenReturn(hashedPassword);
        User savedUser = new User();
        savedUser.setUsername("juancito");
        savedUser.setEmail("juan@correo.cl");
        savedUser.setPassword(hashedPassword);
        savedUser.setActive(true);
        savedUser.setTotalInstitutions(0L);
        savedUser.setAuditsPerformed(0L);
        savedUser.setNotifications(new ArrayList<>());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);
        User result = useCase.createUser(inputUser);
        assertThat(result)
                .extracting(
                        User::getUsername,
                        User::getEmail,
                        User::getPassword,
                        User::isActive,
                        User::getTotalInstitutions,
                        User::getAuditsPerformed,
                        user -> user.getNotifications() != null && user.getNotifications().isEmpty())
                .containsExactly(
                        "juancito",
                        "juan@correo.cl",
                        hashedPassword,
                        true,
                        0L,
                        0L,
                        true);

        Mockito.verify(hasher).hash("contraseña");
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

}
