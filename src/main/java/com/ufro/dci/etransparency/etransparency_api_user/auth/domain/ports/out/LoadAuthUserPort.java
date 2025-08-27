package com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out;

import java.util.Optional;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.model.AuthUserDetails;

public interface LoadAuthUserPort {
    Optional<AuthUserDetails> loadByUsername(String username);
}
