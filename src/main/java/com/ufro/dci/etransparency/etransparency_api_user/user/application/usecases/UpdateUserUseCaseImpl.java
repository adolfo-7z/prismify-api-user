package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.PartialUpdateMapper;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.User;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.UpdateUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del caso de uso para actualizar y gestionar usuarios.
 * <p>
 * Esta clase proporciona la lógica necesaria para:
 * <ul>
 * <li>Actualizar parcialmente los datos de un usuario.</li>
 * <li>Activar o desactivar el estado de un usuario.</li>
 * <li>Incrementar la cantidad de auditorías realizadas por un usuario.</li>
 * </ul>
 *
 * <p>
 * Se apoya en {@link UserRepository} para acceder y modificar los datos
 * persistidos de los usuarios.
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;

    /**
     * Actualiza los datos de un usuario identificado por su ID.
     * Solo los campos no nulos en {@code updatedUser} serán copiados
     * sobre la entidad existente.
     *
     * @param id          identificador único del usuario a actualizar
     * @param updatedUser objeto con los campos a modificar
     * @return el usuario actualizado
     * @throws UserNotFoundException si no existe un usuario con el ID proporcionado
     */
    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id);
        PartialUpdateMapper.copyNonNullFields(updatedUser, user);
        userRepository.update(user);
        return user;
    }

    /**
     * Alterna el estado de un usuario entre activo e inactivo.
     *
     * @param id identificador único del usuario
     * @return un mensaje indicando el nuevo estado del usuario:
     *         <ul>
     *         <li>{@code "User deactivated"} si el usuario estaba activo.</li>
     *         <li>{@code "User activated"} si el usuario estaba inactivo.</li>
     *         </ul>
     * @throws UserNotFoundException si no existe un usuario con el ID proporcionado
     */
    @Override
    public String toggleUserStatus(Long id) {
        User user = userRepository.findById(id);
        boolean wasActive = user.isActive();
        user.setActive(!wasActive);
        userRepository.update(user);
        return wasActive ? "User deactivated" : "User activated";
    }

    /**
     * Incrementa en uno el contador de auditorías realizadas por el usuario.
     *
     * @param id identificador único del usuario
     * @throws UserNotFoundException si no existe un usuario con el ID proporcionado
     */
    @Override
    public void incrementAuditsPerformed(Long id) {
        User user = userRepository.findById(id);
        Long auditsPerformed = user.getAuditsPerformed();
        user.setAuditsPerformed(auditsPerformed + 1L);
        userRepository.update(user);
    }

}
