package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ufro.dci.etransparency.etransparency_api_user.auth.application.services.AuthService;
import com.ufro.dci.etransparency.etransparency_api_user.auth.application.usecases.*;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.*;

@Configuration
public class AuthConfig {

    @Bean
    public AuthService authService(LoadAuthUserPort loadAuthUserPort,
            PasswordMatcher matcher,
            TokenProvider tokenProvider) {
        return new AuthService(new LoginUseCaseImpl(loadAuthUserPort, matcher, tokenProvider),
                new ValidateTokenUseCaseImpl(tokenProvider));
    }

}
