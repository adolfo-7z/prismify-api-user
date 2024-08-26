package com.ufro.dci.etransparency.etransparency_api_user.dtos.evidence;

import com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension.DimensionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.process.ProcessDTO;
import com.ufro.dci.etransparency.etransparency_api_user.models.evidence.Evidence.EvidenceStatus;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvidenceDTO {

    private Long id;

    private String fileName;

    private String fileType;

    private String conditions;

    private String comment;

    private EvidenceStatus evidenceStatus;

    private DimensionDTO dimension;

    private ProcessDTO process;
}
