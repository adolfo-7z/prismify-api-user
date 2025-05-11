package com.ufro.dci.etransparency.etransparency_api_user.dtos.institution;

import lombok.*;

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
