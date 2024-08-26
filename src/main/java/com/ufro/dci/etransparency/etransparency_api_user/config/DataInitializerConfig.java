package com.ufro.dci.etransparency.etransparency_api_user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity.UserRole;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializerConfig {

    @Value("${spring.application.default-username}")
    private String defaultUsername;

    @Value("${spring.application.default-email}")
    private String defaultEmail;

    @Value("${spring.application.default-password}")
    private String defaultPassword;

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (administratorRepository.existsByUsername(defaultUsername)) {
                return;
            }

            Administrator admin = new Administrator();
            admin.setUsername(defaultUsername);
            admin.setEmail(defaultEmail);
            admin.setPassword(passwordEncoder.encode(defaultPassword)); 
            admin.setRole(UserRole.ADMIN);
            admin.setActive(true);

            administratorRepository.save(admin);
        };
    }
}