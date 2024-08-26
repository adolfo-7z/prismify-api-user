package com.ufro.dci.etransparency.etransparency_api_user.dtos.result;

import java.util.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemResultDTO {

    private Long id;

    private List<String> dimensions;

    private List<Double> dimensionsAverage;

}
