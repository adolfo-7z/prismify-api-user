package com.ufro.dci.etransparency.etransparency_api_user.repositories.process;

import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process.ProcessStatus;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    @Query("SELECT COUNT(p) FROM Process p WHERE p.status = :status")
    Long countByStatus(@Param("status") Process.ProcessStatus status);

    List<Process> findByInstitution(Institution institution);

    List<Process> findByInstitutionAndStatusIn(Institution institution, List<ProcessStatus> statuses);

    List<Process> findByInstitutionAndStatus(Institution institution, ProcessStatus status);

    Process findSingleByInstitutionAndStatus(Institution institution, ProcessStatus status);

    List<Process> findAllByStatus(Process.ProcessStatus status);

    Process findByRequestStatus(Process.RequestStatus requestStatus);

    Page<Process> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Process> findByNameContainingIgnoreCase(String name);

    Page<Process> findById(Long id, Pageable pageable);

}
