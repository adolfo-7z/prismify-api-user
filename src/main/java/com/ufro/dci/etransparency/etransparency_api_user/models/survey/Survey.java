package com.ufro.dci.etransparency.etransparency_api_user.models.survey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;

import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Survey {
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @OneToOne(mappedBy = "survey")
    @ToString.Exclude
    private Process process;

    private int responseCount=0;
}
