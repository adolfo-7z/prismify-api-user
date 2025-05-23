package com.ufro.dci.etransparency.etransparency_api_user.models.report;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Report {

    @Column(columnDefinition = "TEXT")
    private String latexBody;
    
}
