package com.ufro.dci.etransparency.etransparency_api_user.models.survey;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Section {
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "dimension_id")
    @ToString.Exclude
    private Dimension dimension;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    @ToString.Exclude
    private Survey survey;
}
