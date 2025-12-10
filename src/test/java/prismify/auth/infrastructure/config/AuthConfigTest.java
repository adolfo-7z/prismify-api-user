package prismify.auth.infrastructure.config;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import prismify.auth.application.services.AuthService;
import prismify.auth.domain.model.AuthUserDetails;
import prismify.auth.domain.ports.out.*;

@ExtendWith(SpringExtension.class)
@Import(AuthConfig.class)
class AuthConfigTest {

    @MockitoBean
    private LoadAuthUserPort loadAuthUserPort;

    @MockitoBean
    private PasswordMatcher matcher;

    @MockitoBean
    private TokenProvider tokenProvider;

    @Autowired
    private AuthService authService;

    @Test
    void authServiceShouldBeInitializedCorrectly() {
        assertThat(authService).isNotNull();
    }

    @Test
    void loginShouldDelegateToLoginUseCase() throws InvalidCredentialsException {
        String username = "techpriest";
        String password = "servitor123";
        String expectedToken = "divine-token";
        when(tokenProvider.generateToken(any())).thenReturn(expectedToken);
        when(loadAuthUserPort.loadByUsername(username))
                .thenReturn(Optional.of(new AuthUserDetails(null, username, password, expectedToken, true)));
        when(matcher.matches(password, password)).thenReturn(true);
        String result = authService.login(username, password);
        assertThat(result).isEqualTo(expectedToken);
    }

    @Test
    void validateTokenShouldDelegateToValidateTokenUseCase() {
        String token = "sacred-token";
        when(tokenProvider.validateToken(token)).thenReturn(true);
        boolean result = authService.validateToken(token);
        assertThat(result).isTrue();
    }

}
