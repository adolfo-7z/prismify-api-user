package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import java.util.Date;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
