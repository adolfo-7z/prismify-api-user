package com.ufro.dci.etransparency.etransparency_api_user.dtos.institution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleInstitutionDTO {
    private Long id;
    private String name;
    private String acronym;
    private String color;
    private String city;
}
