package prismify.user.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import prismify.user.domain.models.Role;
import prismify.user.infrastructure.entities.UserEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByRole(Role role);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByRole(Role role);

    @Query(value = """
            SELECT
                u.id,
                u.username,
                u.email,
                u.is_active,
                u.role,
                u.created_at,
                u.updated_at,
                u.total_institutions,
                u.audits_performed
            FROM users u
            WHERE
                (:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%')))
                AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
                AND (:active IS NULL OR u.is_active = :active)
                AND (:role IS NULL OR u.role = CAST(:role AS text))
            ORDER BY
                CASE WHEN :date = 'asc'  THEN u.created_at END ASC,
                CASE WHEN :date = 'desc' THEN u.created_at END DESC
            """, countQuery = """
            SELECT COUNT(*)
            FROM users u
            WHERE
                (:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%')))
                AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
                AND (:active IS NULL OR u.is_active = :active)
                AND (:role IS NULL OR u.role = CAST(:role AS text))
            """, nativeQuery = true)
    Page<Object[]> findAllUsersFiltered(
            @Param("username") String username,
            @Param("email") String email,
            @Param("active") Boolean active,
            @Param("role") String role,
            @Param("date") String date,
            Pageable pageable);
}
