package com.ufro.dci.etransparency.etransparency_api_user.models.survey;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private Section section;


    private int stronglyDisagreeCount = 0;
    private int disagreeCount = 0;
    private int neutralCount = 0;
    private int agreeCount = 0;
    private int stronglyAgreeCount = 0;
}
