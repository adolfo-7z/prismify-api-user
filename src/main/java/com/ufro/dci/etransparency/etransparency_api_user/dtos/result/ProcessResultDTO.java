package com.ufro.dci.etransparency.etransparency_api_user.dtos.result;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResultDTO {

    private Long id;

    private boolean isActive = true;
    
    private Long levelValue;

    private Long levelPercentage;

    private List<String> dimensions;

    private List<Double> dimensionsAverage;

    private Long percentageAnswered;

    private Process process;

}
