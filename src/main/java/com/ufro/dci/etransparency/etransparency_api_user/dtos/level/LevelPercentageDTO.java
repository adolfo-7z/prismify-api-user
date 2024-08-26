package com.ufro.dci.etransparency.etransparency_api_user.dtos.level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LevelPercentageDTO {

    private int level;
    
    private double percentage;

    private Long nInstitutions;

}
