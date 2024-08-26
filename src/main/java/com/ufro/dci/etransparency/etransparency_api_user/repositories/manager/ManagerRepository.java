package com.ufro.dci.etransparency.etransparency_api_user.repositories.manager;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.manager.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Manager> findByUsername(String username);
}
