package com.ufro.dci.etransparency.etransparency_api_user.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ufro.dci.etransparency.etransparency_api_user.auth.domain.ports.out.LoadAuthUserPort;

@Configuration
public class SecurityBeansConfig {

    @Bean
    public UserDetailsService userDetailsService(LoadAuthUserPort loadAuthUserPort) {
        return username -> loadAuthUserPort.loadByUsername(username)
                .map(user -> User
                        .withUsername(user.username())
                        .password(user.hashedPassword())
                        .roles(user.role())
                        .accountLocked(!user.isActive())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
