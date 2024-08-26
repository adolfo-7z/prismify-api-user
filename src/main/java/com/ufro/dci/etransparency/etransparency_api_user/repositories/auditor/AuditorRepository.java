package com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor;

import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Long>{
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Auditor> findByUsername(String username);
    Page<Auditor> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
