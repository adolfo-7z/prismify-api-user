package com.ufro.dci.etransparency.etransparency_api_user.auth.application.usecases;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.in.ValidateTokenUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.TokenProvider;

public class ValidateTokenUseCaseImpl implements ValidateTokenUseCase{

    private final TokenProvider tokenProvider;

    public ValidateTokenUseCaseImpl(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }
    
}
