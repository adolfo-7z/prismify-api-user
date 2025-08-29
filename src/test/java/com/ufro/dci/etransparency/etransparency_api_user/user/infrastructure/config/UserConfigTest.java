package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.config;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.services.UserService;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserIfNotExistsUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.PasswordHasher;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserEmailPort;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.repositories.UserJpaRepositoryAdapter;

@ExtendWith(SpringExtension.class)
@Import(UserConfig.class)
class UserConfigTest {

    @MockitoBean
    private UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @MockitoBean
    private PasswordHasher passwordHasher;

    @MockitoBean
    private UserEmailPort userEmailPort;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CreateUserIfNotExistsUseCase createUserIfNotExistsUseCase;

    @Test
    void userRepositoryBeanShouldBeSameAsAdapter() {
        assertThat(userRepository).isSameAs(userJpaRepositoryAdapter);
    }

    @Test
    void userServiceShouldBeInitializedCorrectly() {
        assertThat(userService).isNotNull();
    }

    @Test
    void createUserIfNotExistsUseCaseShouldBeInitializedCorrectly() {
        assertThat(createUserIfNotExistsUseCase).isNotNull();
    }

}
