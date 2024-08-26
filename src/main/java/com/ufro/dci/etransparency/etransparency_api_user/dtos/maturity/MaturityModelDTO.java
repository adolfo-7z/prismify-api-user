package com.ufro.dci.etransparency.etransparency_api_user.dtos.maturity;

import java.util.*;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.dimension.DimensionDTO;
import com.ufro.dci.etransparency.etransparency_api_user.dtos.process.ProcessDTO;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaturityModelDTO {
    private Long id;

    private boolean isActive = true;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 150, message = "Description cannot exceed 150 characters")
    private String description;

    @NotNull(message = "Time limit cannot be null")
    private Long timeLimit;

    private Date createdAt;

    private Date updatedAt;

    private byte[] modelFileData;

    private String modelFileName;

    private String modelFileType;

    private List<ProcessDTO> processes;

    private List<DimensionDTO> dimensions;
}