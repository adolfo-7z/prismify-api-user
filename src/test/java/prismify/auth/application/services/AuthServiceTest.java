package prismify.auth.application.services;

import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.*;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import prismify.auth.domain.ports.in.LoginUseCase;
import prismify.auth.domain.ports.in.ValidateTokenUseCase;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private ValidateTokenUseCase validateTokenUseCase;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldDelegateLoginToLoginUseCase() throws InvalidCredentialsException {
        String username = "juancito";
        String rawPassword = "password";
        String expectedToken = "jwt-token";
        Mockito.when(loginUseCase.login(username, rawPassword)).thenReturn(expectedToken);
        String result = authService.login(username, rawPassword);
        assertThat(result).isEqualTo(expectedToken);
        verify(loginUseCase).login(username, rawPassword);
    }

    @Test
    void shouldDelegateValidateTokenToValidateTokenUseCase() {
        String token = "token";
        Mockito.when(validateTokenUseCase.validateToken(token)).thenReturn(true);
        boolean result = authService.validateToken(token);
        assertThat(result).isTrue();
        verify(validateTokenUseCase).validateToken(token);
    }

    @Test
    void shouldPropagateInvalidCredentialsException() throws InvalidCredentialsException {
        String username = "notjuancito";
        String rawPassword = "false-pass";
        Mockito.when(loginUseCase.login(username, rawPassword)).thenThrow(new InvalidCredentialsException());
        assertThatThrownBy(() -> authService.login(username, rawPassword))
            .isInstanceOf(InvalidCredentialsException.class);
    }
    
}
