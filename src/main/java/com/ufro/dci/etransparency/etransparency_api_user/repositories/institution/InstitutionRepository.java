package com.ufro.dci.etransparency.etransparency_api_user.repositories.institution;

import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.auditor.Auditor;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    List<Institution> findByAuditor(Auditor auditor);
    List<Institution> findAllByLevel(Long level);
    Page<Institution> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
