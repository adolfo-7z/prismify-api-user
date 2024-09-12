package com.ufro.dci.etransparency.etransparency_api_user.dtos.result;

import java.util.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterResultDTO {

    private Long id;

    private double centroid;
    
    private List<Double> points;

    private int weight;

    private ProcessResultDTO processResult;
    
}
