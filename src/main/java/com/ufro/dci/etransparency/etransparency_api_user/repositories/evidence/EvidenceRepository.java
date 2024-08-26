package com.ufro.dci.etransparency.etransparency_api_user.repositories.evidence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.evidence.EvidenceSummary;
import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.evidence.Evidence;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    @Query("SELECT COUNT(e) FROM Evidence e WHERE e.process.id = :processId AND e.dimension.id = :dimensionId")
    Long countByProcessIdAndDimensionId(Long processId, Long dimensionId);

    List<EvidenceSummary> findSummaryByProcessAndDimension(Process process, Dimension dimension);
    
}
