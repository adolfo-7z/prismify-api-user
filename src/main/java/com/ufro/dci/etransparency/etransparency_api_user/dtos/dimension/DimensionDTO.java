package com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionDTO {

    private Long id;

    @JsonIgnore
    private boolean isActive = true;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    private String jsonQuestions;

    private String jsonEvidenceRequirements;
    
}
