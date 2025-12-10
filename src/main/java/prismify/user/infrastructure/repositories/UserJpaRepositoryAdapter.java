package prismify.user.infrastructure.repositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prismify.user.domain.models.*;
import prismify.user.domain.ports.out.UserRepository;
import prismify.user.infrastructure.controllers.exception.custom.UserNotFoundException;
import prismify.user.infrastructure.entities.NotificationEntity;
import prismify.user.infrastructure.entities.UserEntity;

/**
 * Adaptador que implementa {@link UserRepository} utilizando un
 * {@link UserJpaRepository}.
 * Esta clase se encarga de la persistencia de los usuarios en la base de datos
 * y
 * transforma las entidades JPA en objetos de dominio {@link User} y viceversa.
 * 
 * <p>
 * Las operaciones de lectura se marcan como {@code readOnly=true} para
 * optimizar
 * el rendimiento de las transacciones.
 * 
 * @author Adolfo Plaza
 */
@Component
public class UserJpaRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserJpaRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Guarda un usuario en la base de datos.
     * 
     * @param user Usuario a guardar.
     * @return El usuario guardado convertido desde la entidad JPA.
     */
    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param id ID del usuario a buscar.
     * @return El usuario encontrado.
     * @throws UserNotFoundException Si no se encuentra el usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return jpaRepository.findById(id).map(UserEntity::toDomain)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario a buscar.
     * @return El usuario encontrado.
     * @throws UserNotFoundException Si no se encuentra el usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(UserEntity::toDomain).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico del usuario a buscar.
     * @return El usuario encontrado.
     * @throws UserNotFoundException Si no se encuentra el usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserEntity::toDomain).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByRole(Role role) {
        return jpaRepository.findByRole(role).map(UserEntity::toDomain).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Verifica si existe un usuario con un correo electrónico dado.
     * 
     * @param email Correo electrónico a verificar.
     * @return {@code true} si existe un usuario con ese correo, {@code false} en
     *         caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    /**
     * Verifica si existe un usuario con un nombre de usuario dado.
     * 
     * @param username nombre de usuario a verificar.
     * @return {@code true} si existe un usuario con ese nombre, {@code false} en
     *         caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    /**
     * Verifica si existe algún usuario con un rol específico.
     * 
     * @param role Rol a verificar.
     * @return {@code true} si existe al menos un usuario con ese rol, {@code false}
     *         en caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByRole(Role role) {
        return jpaRepository.existsByRole(role);
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     * 
     * @return Lista de todos los usuarios.
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return jpaRepository.findAll().stream().map(UserEntity::toDomain).toList();
    }

    /**
     * Obtiene una lista paginada de usuarios, ordenada por la fecha de
     * actualización.
     * 
     * @param page Número de página (0-indexed).
     * @param size Tamaño de la página.
     * @param date Ordenamiento por fecha ("asc" o "desc").
     * @param name Nombre de usuario (no utilizado actualmente en el filtrado).
     * @return Lista de usuarios en la página solicitada.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(int page, int size, String username, String email, String date, Boolean active,
            Role role) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Object[]> result = jpaRepository.findAllUsersFiltered(
                username,
                email,
                active,
                role != null ? role.name() : null,
                date,
                pageable);

        return result.map(row -> {
            User u = new User();

            u.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
            u.setUsername((String) row[1]);
            u.setEmail((String) row[2]);
            u.setActive(row[3] != null && (Boolean) row[3]);

            u.setRole(row[4] != null ? Role.valueOf((String) row[4]) : null);

            u.setCreatedAt(toLocalDateTime(row[5]));
            u.setUpdatedAt(toLocalDateTime(row[6]));

            u.setTotalInstitutions(row[7] != null ? ((Number) row[7]).longValue() : null);
            u.setAuditsPerformed(row[8] != null ? ((Number) row[8]).longValue() : null);

            u.setNotifications(List.of());
            u.setPassword(null);
            u.setPhoneNumber(null);
            u.setRecoveryCode(null);
            u.setRecoveryCodeExpiration(null);

            u.setPosition(null);
            u.setRut(null);
            u.setCity(null);
            u.setColor(null);
            u.setAcronym(null);

            return u;
        });
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value == null)
            return null;
        if (value instanceof Timestamp ts)
            return ts.toLocalDateTime();
        return null;
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * 
     * @param user Usuario con los datos actualizados.
     * @return {@link Optional} con el usuario actualizado, o
     *         {@code Optional.empty()} si no se encuentra.
     */
    @Override
    @Transactional
    public Optional<User> update(User user) {
        return jpaRepository.findById(user.getId()).map(existing -> {
            existing.setUsername(user.getUsername());
            existing.setPassword(user.getPassword());
            existing.setEmail(user.getEmail());
            existing.setPhoneNumber(user.getPhoneNumber());
            existing.setPosition(user.getPosition());
            existing.setRut(user.getRut());
            existing.setCity(user.getCity());
            existing.setColor(user.getColor());
            existing.setAcronym(user.getAcronym());
            existing.getNotifications().clear();
            existing.getNotifications().addAll(
                    user.getNotifications().stream()
                            .map(n -> NotificationEntity.fromDomain(n, existing))
                            .toList());
            existing.setAuditsPerformed(user.getAuditsPerformed());
            existing.setActive(user.isActive());
            return Optional.of(jpaRepository.save(existing).toDomain());
        }).orElse(Optional.empty());
    }

    /**
     * Elimina un usuario por su ID.
     * 
     * @param id ID del usuario a eliminar.
     * @return {@code true} si el usuario fue eliminado, {@code false} si no
     *         existía.
     */
    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
