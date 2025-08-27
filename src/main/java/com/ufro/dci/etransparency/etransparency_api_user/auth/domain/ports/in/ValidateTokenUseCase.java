package com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.in;

public interface ValidateTokenUseCase {
    boolean validateToken(String token);
}
