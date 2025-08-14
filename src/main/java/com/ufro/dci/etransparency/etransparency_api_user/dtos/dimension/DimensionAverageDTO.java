package com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionAverageDTO {

    private Long id;

    private String name;

    private Double average;
}
