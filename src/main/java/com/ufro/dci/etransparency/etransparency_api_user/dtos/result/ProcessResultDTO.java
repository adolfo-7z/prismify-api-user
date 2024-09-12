package com.ufro.dci.etransparency.etransparency_api_user.dtos.result;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.process.ProcessDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResultDTO {

    private Long id;

    @JsonIgnore
    private boolean isActive = true;

    private Double peopleAnswered;

    private List<Long> dimensionLevels;

    private Long institutionLevel;

    private ProcessDTO process;

    private List<ClusterResultDTO> clusters;

}
