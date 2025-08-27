package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases.*;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserIfNotExistsUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.*;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.repositories.UserJpaRepositoryAdapter;

@Configuration
public class UserConfig {

    @Bean
    UserService userService(UserRepository userRepository, PasswordHasher hasher) {
        return new UserService(new CreateUserUseCaseImpl(userRepository, hasher),
                new RetrieveUserUseCaseImpl(userRepository),
                new UpdateUserUseCaseImpl(userRepository), new DeleteUserUseCaseImpl(userRepository));
    }

    @Bean
    UserRepository userRepository(UserJpaRepositoryAdapter adapter) {
        return adapter;
    }

    @Bean
    CreateUserIfNotExistsUseCase createUserIfNotExistsUseCase(UserRepository userRepository) {
        return new CreateUserIfNotExistsUseCaseImpl(userRepository);
    }
    
}
