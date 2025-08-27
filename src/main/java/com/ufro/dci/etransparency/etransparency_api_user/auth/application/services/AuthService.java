package com.ufro.dci.etransparency.etransparency_api_user.auth.application.services;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.in.LoginUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.in.ValidateTokenUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthService implements LoginUseCase, ValidateTokenUseCase {

    private final LoginUseCase loginUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;

    @Override
    public String login(String username, String rawPassword) throws InvalidCredentialsException {
        return loginUseCase.login(username, rawPassword);
    }

    @Override
    public boolean validateToken(String token) {
        return validateTokenUseCase.validateToken(token);
    }
}
