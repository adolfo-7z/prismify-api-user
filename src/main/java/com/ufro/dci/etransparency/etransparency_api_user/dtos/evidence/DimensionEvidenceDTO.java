package com.ufro.dci.etransparency.etransparency_api_user.dtos.evidence;

import lombok.*;
import java.util.List;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension.DimensionDTO;

@Data
public class DimensionEvidenceDTO {
    private DimensionDTO dimension;
    private List<EvidenceDTO> evidence;
}
