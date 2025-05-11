package com.ufro.dci.etransparency.etransparency_api_user.models.dimension;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EvidenceRequirement {

    private String title;
    private String requirement;
    
}
