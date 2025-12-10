package prismify.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import prismify.auth.domain.ports.out.LoadAuthUserPort;

/**
 * Configuración de beans relacionados con la seguridad de la aplicación.
 * 
 * @author Adolfo Plaza
 */
@Configuration
public class SecurityBeansConfig {

    /**
     * Bean que proporciona un servicio de detalles de usuario para Spring Security.
     * <p>
     * Este servicio utiliza un {@link LoadAuthUserPort} para cargar la información
     * del usuario desde la fuente de datos correspondiente. Si el usuario existe,
     * se construye un objeto
     * {@link org.springframework.security.core.userdetails.User}
     * con su nombre de usuario, contraseña hasheada y roles.
     * Si el usuario no existe, se lanza una {@link UsernameNotFoundException}.
     *
     * @param loadAuthUserPort el puerto de carga de usuarios autenticables
     * @return un {@link UserDetailsService} para la autenticación de usuarios
     * @throws UsernameNotFoundException si no se encuentra el usuario con el nombre
     *                                   proporcionado
     */
    @Bean
    UserDetailsService userDetailsService(LoadAuthUserPort loadAuthUserPort) {
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
