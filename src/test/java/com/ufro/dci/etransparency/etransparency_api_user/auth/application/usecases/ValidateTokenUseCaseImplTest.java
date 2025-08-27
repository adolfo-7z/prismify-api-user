package com.ufro.dci.etransparency.etransparency_api_user.auth.application.usecases;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.TokenProvider;

@ExtendWith(MockitoExtension.class)
class ValidateTokenUseCaseImplTest {

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private ValidateTokenUseCaseImpl validateTokenUseCase;

    @Test
    void shouldReturnTrue_WhenTokenIsValid() {
        String validToken = "valid-token";
        Mockito.when(tokenProvider.validateToken(validToken)).thenReturn(true);
        boolean result = validateTokenUseCase.validateToken(validToken);
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalse_WhenTokenIsInvalid() {
        String invalidToken = "corrupted-token";
        Mockito.when(tokenProvider.validateToken(invalidToken)).thenReturn(false);
        boolean result = validateTokenUseCase.validateToken(invalidToken);
        assertThat(result).isFalse();
    }

}
