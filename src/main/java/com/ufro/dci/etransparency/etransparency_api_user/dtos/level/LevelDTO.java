package com.ufro.dci.etransparency.etransparency_api_user.dtos.level;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension.DimensionDTO;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LevelDTO {

    private Long id;

    @JsonIgnore
    private boolean isActive = true;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    private List<DimensionDTO> dimensions;
    
}
