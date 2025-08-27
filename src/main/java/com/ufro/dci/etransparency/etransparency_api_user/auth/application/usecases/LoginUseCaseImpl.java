package com.ufro.dci.etransparency.etransparency_api_user.auth.application.usecases;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model.AuthUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.in.LoginUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final LoadAuthUserPort loadAuthUserPort;
    private final PasswordMatcher hasher;
    private final TokenProvider tokenProvider;

    @Override
    public String login(String username, String rawPassword) throws InvalidCredentialsException {
        AuthUserDetails user = loadAuthUserPort.loadByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);
        if (!user.isActive() || !hasher.matches(rawPassword, user.hashedPassword())) {
            throw new InvalidCredentialsException();
        }
        return tokenProvider.generateToken(user);
    }
}
