package com.ufro.dci.etransparency.etransparency_api_user.dtos.recommendation;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.process.ProcessDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {

    private Long id;

    private String dimension;

    private Long currentLevel;
    
    private Long targetLevel;

    private String recommendation;

    private Date createdAt;

    private Date updatedAt;

    @JsonIgnore
    private ProcessDTO process;
    
}
