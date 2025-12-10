package prismify.user.application.usecases;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import prismify.user.domain.models.User;
import prismify.user.domain.ports.out.*;
import prismify.user.infrastructure.controllers.exception.custom.InvalidPasswordException;
import prismify.user.infrastructure.controllers.exception.custom.InvalidRecoveryCodeException;

@ExtendWith(MockitoExtension.class)
class PasswordRecoveryUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEmailPort mailPort;

    @Mock
    private PasswordHasher hasher;

    @InjectMocks
    private PasswordRecoveryUseCaseImpl useCase;

    @Test
    void sendRecoveryCode_ShouldGenerateCode_SaveUser_AndSendEmail() {
        User user = new User();
        user.setEmail("prueba@correo.cl");
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        useCase.sendRecoveryCode("prueba@correo.cl");
        assertThat(user.getRecoveryCode()).isNotNull();
        assertThat(user.getRecoveryCode()).hasSize(6);
        assertThat(user.getRecoveryCodeExpiration()).isAfter(LocalDateTime.now());
        verify(userRepository).save(user);
        verify(mailPort).sendRecoveryCodeEmail(eq("prueba@correo.cl"), anyString());
    }

    @Test
    void validateRecoveryCode_ShouldThrow_WhenRecoveryCodeNull() {
        User user = new User();
        user.setRecoveryCode(null);
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        assertThatThrownBy(() -> useCase.validateRecoveryCode("prueba@correo.cl", "123456"))
                .isInstanceOf(InvalidRecoveryCodeException.class);
    }

    @Test
    void validateRecoveryCode_ShouldThrow_WhenRecoveryCodeEmpty() {
        User user = new User();
        user.setRecoveryCode("");
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        assertThatThrownBy(() -> useCase.validateRecoveryCode("prueba@correo.cl", "123456"))
                .isInstanceOf(InvalidRecoveryCodeException.class);
    }

    @Test
    void validateRecoveryCode_ShouldThrow_WhenCodeExpired() {
        User user = new User();
        user.setRecoveryCode("ABC123");
        user.setRecoveryCodeExpiration(LocalDateTime.now().minusMinutes(1));
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        assertThatThrownBy(() -> useCase.validateRecoveryCode("prueba@correo.cl", "ABC123"))
                .isInstanceOf(InvalidRecoveryCodeException.class)
                .hasMessageContaining("expired");
    }

    @Test
    void validateRecoveryCode_ShouldThrow_WhenCodeDoesNotMatch() {
        User user = new User();
        user.setRecoveryCode("CORRECTO");
        user.setRecoveryCodeExpiration(LocalDateTime.now().plusMinutes(10));
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        assertThatThrownBy(() -> useCase.validateRecoveryCode("prueba@correo.cl", "INCORRECTO"))
                .isInstanceOf(InvalidRecoveryCodeException.class);
    }

    @Test
    void validateRecoveryCode_ShouldPass_WhenValidCode() {
        User user = new User();
        user.setRecoveryCode("ABC123");
        user.setRecoveryCodeExpiration(LocalDateTime.now().plusMinutes(10));
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        useCase.validateRecoveryCode("prueba@correo.cl", "ABC123");
    }

    @Test
    void validateNewPassword_ShouldThrow_WhenPasswordsDoNotMatch() {
        User user = new User();
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        assertThatThrownBy(() -> useCase.validateNewPassword("prueba@correo.cl", "a", "b"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("do not match");
    }

    @Test
    void validateNewPassword_ShouldThrow_WhenNewPasswordEqualsOld() {
        User user = new User();
        user.setPassword("HASHED");
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        when(hasher.matches("samePass", "HASHED")).thenReturn(true);
        assertThatThrownBy(() -> useCase.validateNewPassword("prueba@correo.cl", "samePass", "samePass"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("same as previous");
    }

    @Test
    void validateNewPassword_ShouldUpdatePassword_SaveUser_AndSendEmail() {
        User user = new User();
        user.setEmail("prueba@correo.cl");
        user.setPassword("OLD_HASH");
        when(userRepository.findByEmail("prueba@correo.cl")).thenReturn(user);
        when(hasher.matches("newPass", "OLD_HASH")).thenReturn(false);
        when(hasher.hash("newPass")).thenReturn("NEW_HASH");
        useCase.validateNewPassword("prueba@correo.cl", "newPass", "newPass");
        assertThat(user.getPassword()).isEqualTo("NEW_HASH");
        verify(userRepository).save(user);
        verify(mailPort).sendNewPasswordAlert("prueba@correo.cl");
    }

}
