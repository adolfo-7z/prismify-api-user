package com.ufro.dci.etransparency.etransparency_api_user.models.level;

import java.util.*;

import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.survey.Question;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActive;

    @NotNull
    @Column(length = 50)
    private String name;

    private Long trueLevelValue;

    @Column(columnDefinition = "TEXT")
    private String questions;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    //Respuestas
    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> answers;
}
