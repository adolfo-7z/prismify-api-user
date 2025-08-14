package com.ufro.dci.etransparency.etransparency_api_user.models.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Report {

    @Column(columnDefinition = "TEXT")
    private String latexBody;
    
}
