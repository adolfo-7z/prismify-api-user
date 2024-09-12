package com.ufro.dci.etransparency.etransparency_api_user.models.survey;

import com.ufro.dci.etransparency.etransparency_api_user.models.level.Level;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", length = 1024)
    private String text;

    @Column(length = 10)
    private String levelName;

    @Column(length = 50)
    private String dimensionName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id")
    private Level level;

    private int stronglyDisagreeCount = 0;
    private int disagreeCount = 0;
    private int neutralCount = 0;
    private int agreeCount = 0;
    private int stronglyAgreeCount = 0;
}
