package com.ufro.dci.etransparency.etransparency_api_user.models.result;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClusterResult {

    @Column(length = 50)
    private String dimensionName;

    private String centroid;

    private List<String> points;

    private int weight;
    
}
