package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SystemResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private List<String> dimensions;

    private List<Double> dimensionsAverage;

}
