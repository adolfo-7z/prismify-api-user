package com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.administrator.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Administrator> findByUsername(String username);
}