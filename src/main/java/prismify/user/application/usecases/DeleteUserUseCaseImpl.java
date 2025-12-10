package prismify.user.application.usecases;

import lombok.RequiredArgsConstructor;
import prismify.user.domain.ports.in.DeleteUserUseCase;
import prismify.user.domain.ports.out.UserRepository;

/**
 * Implementación del caso de uso para eliminar usuarios del sistema.
 * <p>
 * Esta clase utiliza el repositorio de usuarios para realizar la operación
 * de eliminación según el identificador proporcionado.
 *
 * <p>
 * Retorna un mensaje indicando si la operación fue exitosa o si el usuario
 * con el identificador especificado no fue encontrado.
 *
 * @author Adolfo Plaza
 */
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepository userRepository;

    /**
     * Elimina un usuario del sistema en base a su identificador único.
     *
     * @param id el identificador único del usuario a eliminar.
     * @return un {@link String} con el mensaje del resultado de la operación:
     *         <ul>
     *         <li>"User deleted" si el usuario fue eliminado correctamente.</li>
     *         <li>"User with the present ID was not found" si no existe un usuario
     *         con el ID entregado.</li>
     *         </ul>
     */
    @Override
    public String deleteUser(Long id) {
        boolean deleted = userRepository.deleteById(id);
        return deleted ? "User deleted" : "User with the present ID was not found";
    }

}
