package com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out;

public interface PasswordHasher {
    String hash(String rawPassword);
}
