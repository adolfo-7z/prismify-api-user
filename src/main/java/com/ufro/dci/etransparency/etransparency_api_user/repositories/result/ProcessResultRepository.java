package com.ufro.dci.etransparency.etransparency_api_user.repositories.result;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import com.ufro.dci.etransparency.etransparency_api_user.models.result.ProcessResult;

@Repository
public interface ProcessResultRepository extends JpaRepository<ProcessResult, Long> {
    ProcessResult findByProcess(Process process);
    List<ProcessResult> findManyByProcess(Process process);
}
