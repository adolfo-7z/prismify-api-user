package com.ufro.dci.etransparency.etransparency_api_user.dtos.process;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process.RequestStatus;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRequestStatusUpdateDTO {

    @NotNull(message = "Request status cannot be null")
    private RequestStatus requestStatus;
    
}
