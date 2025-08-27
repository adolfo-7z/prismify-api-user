package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.PasswordHasher;

@Component
public class BcryptPasswordHasher implements PasswordHasher{

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
}
