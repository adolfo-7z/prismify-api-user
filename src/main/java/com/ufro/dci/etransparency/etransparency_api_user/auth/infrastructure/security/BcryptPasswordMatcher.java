package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.PasswordMatcher;

@Component
public class BcryptPasswordMatcher implements PasswordMatcher {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

}
