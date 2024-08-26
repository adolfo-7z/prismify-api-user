package com.ufro.dci.etransparency.etransparency_api_user.dtos.process;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process.ProcessStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStatusUpdateDTO {

    @NotNull(message = "Status cannot be null")
    private ProcessStatus status;
}

