package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClusterResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String dimensionName;

    private double centroid;
    
    private List<Double> points;

    private int weight;

    @ManyToOne
    @JoinColumn(name = "process_result_id", referencedColumnName = "id")
    private ProcessResult processResult;
    
}
