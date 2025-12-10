package prismify.auth.application.usecases;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import prismify.auth.domain.model.AuthUserDetails;
import prismify.auth.domain.ports.out.LoadAuthUserPort;
import prismify.auth.domain.ports.out.PasswordMatcher;
import prismify.auth.domain.ports.out.TokenProvider;
import prismify.auth.infrastructure.controllers.exception.custom.InvalidCredentialsException;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {

    @Mock
    private LoadAuthUserPort loadAuthUserPort;

    @Mock
    private PasswordMatcher hasher;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    private final String username = "juancito";
    private final String rawPassword = "oasswird";
    private final String role = "MANAGER";
    private final String hashedPassword = "hashed-password";

    @Test
    void shouldLoginSuccessfully_WhenCredentialsAreValidAndUserIsActive() throws InvalidCredentialsException {
        AuthUserDetails user = new AuthUserDetails(1L, username, hashedPassword, role, true);
        String expectedToken = "token-de-prueba";
        Mockito.when(loadAuthUserPort.loadByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(hasher.matches(rawPassword, hashedPassword)).thenReturn(true);
        Mockito.when(tokenProvider.generateToken(user)).thenReturn(expectedToken);
        String token = loginUseCase.login(username, rawPassword);
        assertThat(token).isEqualTo(expectedToken);
    }

    @Test
    void shouldThrowInvalidCredentialsException_WhenUserDoesNotExist() {
        Mockito.when(loadAuthUserPort.loadByUsername(username)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> loginUseCase.login(username, rawPassword))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void shouldThrowInvalidCredentialsException_WhenUserIsInactive() {
        AuthUserDetails user = new AuthUserDetails(1L, username, hashedPassword, role, false);
        Mockito.when(loadAuthUserPort.loadByUsername(username)).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> loginUseCase.login(username, rawPassword))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void shouldThrowInvalidCredentialsException_WhenPasswordDoesNotMatch() {
        AuthUserDetails user = new AuthUserDetails(1L, username, hashedPassword, role, true);
        Mockito.when(loadAuthUserPort.loadByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(hasher.matches(rawPassword, hashedPassword)).thenReturn(false);
        assertThatThrownBy(() -> loginUseCase.login(username, rawPassword))
                .isInstanceOf(InvalidCredentialsException.class);
    }

}
