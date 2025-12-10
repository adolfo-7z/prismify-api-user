package prismify.user.infrastructure.config;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import prismify.user.application.services.UserService;
import prismify.user.domain.ports.in.CreateUserIfNotExistsUseCase;
import prismify.user.domain.ports.out.PasswordHasher;
import prismify.user.domain.ports.out.UserEmailPort;
import prismify.user.domain.ports.out.UserRepository;
import prismify.user.infrastructure.repositories.UserJpaRepositoryAdapter;

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
