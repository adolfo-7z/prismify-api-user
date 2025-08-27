package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserIfNotExistsUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializerConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final CreateUserIfNotExistsUseCase createUserIfNotExistsUseCase;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${spring.application.default-username}")
    private String defaultUsername;

    @Value("${spring.application.default-email}")
    private String defaultEmail;

    @Value("${spring.application.default-password}")
    private String defaultPassword;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        String hashedPassword = encoder.encode(defaultPassword);
        createUserIfNotExistsUseCase.createAdminIfMissing(defaultUsername, defaultEmail, hashedPassword);
    }

}
