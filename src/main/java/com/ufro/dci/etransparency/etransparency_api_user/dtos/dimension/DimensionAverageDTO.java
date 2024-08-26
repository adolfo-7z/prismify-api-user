package com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionAverageDTO {

    private Long id;

    private String name;

    private String description;

    private Double average;
}
