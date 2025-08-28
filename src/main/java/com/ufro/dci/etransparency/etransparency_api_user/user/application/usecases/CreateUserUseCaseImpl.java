package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import java.util.ArrayList;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.CreateUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.PasswordHasher;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del caso de uso para la creación de usuarios.
 * <p>
 * Esta clase se encarga de inicializar los valores por defecto de un nuevo
 * usuario,
 * se aplica un algoritmo de hash a la contraseña antes de guardarlo
 * en el repositorio.
 *
 * <p>
 * La clase está anotada con {@code @RequiredArgsConstructor}, lo que significa
 * que
 * se inyectarán las dependencias requeridas {@link UserRepository} y
 * {@link PasswordHasher}
 * a través del constructor.
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher hasher;

    /**
     * Crea un nuevo usuario en el sistema.
     * <li>Se encripta la contraseña usando {@link PasswordHasher}.</li>
     * <li>Se guarda el usuario en el {@link UserRepository}.</li>
     * </ul>
     *
     * @param user objeto {@link User} que contiene los datos del nuevo usuario.
     * @return el {@link User} persistido con los valores inicializados y la
     *         contraseña encriptada.
     */
    @Override
    public User createUser(User user) {
        user.setActive(true);
        user.setTotalInstitutions(0L);
        user.setAuditsPerformed(0L);
        user.setNotifications(new ArrayList<>());
        String hashedPssword = hasher.hash(user.getPassword());
        user.setPassword(hashedPssword);
        return userRepository.save(user);
    }

}
