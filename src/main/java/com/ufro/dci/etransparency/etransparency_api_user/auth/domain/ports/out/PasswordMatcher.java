package com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out;

public interface PasswordMatcher {
    boolean matches(String rawPassword, String hashedPassword);
}
