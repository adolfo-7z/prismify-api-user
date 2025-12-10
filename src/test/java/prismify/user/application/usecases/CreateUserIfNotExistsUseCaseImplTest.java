package prismify.user.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import prismify.user.domain.models.Role;
import prismify.user.domain.models.User;
import prismify.user.domain.ports.out.UserRepository;

@ExtendWith(MockitoExtension.class)
class CreateUserIfNotExistsUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserIfNotExistsUseCaseImpl useCase;

    @Test
    void shouldNotCreateAdminIfAlreadyExists() {
        Mockito.when(userRepository.existsByRole(Role.ADMIN)).thenReturn(true);
        useCase.createAdminIfMissing("admin", "admin@correo.cl", "contraseñasegura");
        Mockito.verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldCreateAdminIfNotExists() {
        Mockito.when(userRepository.existsByRole(Role.ADMIN)).thenReturn(false);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        useCase.createAdminIfMissing("admin", "admin@machine.cult", "securePassword");
        Mockito.verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser)
                .extracting(
                        User::getUsername,
                        User::getEmail,
                        User::getPassword,
                        User::getRole,
                        User::isActive,
                        User::getTotalInstitutions,
                        User::getAuditsPerformed,
                        user -> user.getNotifications() != null && user.getNotifications().isEmpty())
                .containsExactly(
                        "admin",
                        "admin@machine.cult",
                        "securePassword",
                        Role.ADMIN,
                        true,
                        0L,
                        0L,
                        true);
    }

}
