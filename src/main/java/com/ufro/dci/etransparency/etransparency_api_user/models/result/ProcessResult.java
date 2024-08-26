package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import java.util.*;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProcessResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    private Long levelValue;

    private Long levelPercentage;

    private List<String> dimensions;

    private List<Double> dimensionsAverage;

    private Long percentageAnswered;

    @OneToOne(mappedBy = "processResult")
    private Process process;
    
}
