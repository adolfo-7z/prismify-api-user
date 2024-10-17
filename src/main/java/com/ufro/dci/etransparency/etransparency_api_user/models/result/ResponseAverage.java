package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.util.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAverage {

    private Double completionPercentage;

    private Long peopleAnswered;

    private Long totalPeopleAnswered;

    private Long realAverage;

    private Long idealAverage;

    private Date date;
    
}
