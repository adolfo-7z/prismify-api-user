package prismify.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import prismify.user.application.services.UserService;
import prismify.user.application.usecases.*;
import prismify.user.domain.ports.in.CreateUserIfNotExistsUseCase;
import prismify.user.domain.ports.out.*;
import prismify.user.infrastructure.repositories.UserJpaRepositoryAdapter;

/**
 * Configuración de beans relacionados con la gestión de usuarios.
 * <p>
 * Esta clase define los beans necesarios para la creación, recuperación,
 * actualización y eliminación de usuarios, así como casos de uso adicionales
 * como la creación de un usuario si no existe.
 * 
 * @author Adolfo Plaza
 */
@Configuration
public class UserConfig {

    /**
     * Crea un bean de {@link UserService} que gestiona la lógica principal de
     * usuarios.
     * <p>
     * Inyecta los casos de uso necesarios para manejar la creación, recuperación,
     * actualización y eliminación de usuarios.
     * 
     * @param userRepository el repositorio de usuarios
     * @param hasher         la utilidad para hashear contraseñas
     * @return una instancia de {@link UserService} configurada con sus casos de uso
     */
    @Bean
    UserService userService(UserRepository userRepository, PasswordHasher hasher, UserEmailPort mailPort) {
        return new UserService(new CreateUserUseCaseImpl(userRepository, hasher),
                new RetrieveUserUseCaseImpl(userRepository),
                new UpdateUserUseCaseImpl(userRepository), new DeleteUserUseCaseImpl(userRepository),
                new PasswordRecoveryUseCaseImpl(userRepository, mailPort, hasher),
                new ManageNotificationUseCaseImpl(userRepository));
    }

    /**
     * Proporciona un bean de {@link UserRepository}.
     * <p>
     * Se delega en el adaptador JPA para la persistencia de usuarios.
     * 
     * @param adapter el adaptador JPA que implementa {@link UserRepository}
     * @return el repositorio de usuarios
     */
    @Bean
    UserRepository userRepository(UserJpaRepositoryAdapter adapter) {
        return adapter;
    }

    /**
     * Crea un bean de {@link CreateUserIfNotExistsUseCase} para la creación
     * condicional de usuarios.
     * <p>
     * Este caso de uso permite crear un usuario solo si no existe previamente en el
     * repositorio.
     * 
     * @param userRepository el repositorio de usuarios
     * @return una instancia de {@link CreateUserIfNotExistsUseCase}
     */
    @Bean
    CreateUserIfNotExistsUseCase createUserIfNotExistsUseCase(UserRepository userRepository) {
        return new CreateUserIfNotExistsUseCaseImpl(userRepository);
    }

}
