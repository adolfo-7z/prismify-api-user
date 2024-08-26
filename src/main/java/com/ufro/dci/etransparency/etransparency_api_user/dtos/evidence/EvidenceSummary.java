package com.ufro.dci.etransparency.etransparency_api_user.dtos.evidence;

import com.ufro.dci.etransparency.etransparency_api_user.models.evidence.Evidence.EvidenceStatus;

public interface EvidenceSummary {
    Long getId();

    String getFileName();

    String getFileType();

    String getConditions();

    String getComment();

    EvidenceStatus getEvidenceStatus();

}
