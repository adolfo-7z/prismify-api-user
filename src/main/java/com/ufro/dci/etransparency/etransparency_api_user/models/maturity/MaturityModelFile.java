package com.ufro.dci.etransparency.etransparency_api_user.models.maturity;

import java.sql.Blob;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaturityModelFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "file_data")
    private Blob fileData;

    @Column(columnDefinition = "TEXT")
    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String fileType;

    @OneToOne(mappedBy = "maturityModelFile", cascade = CascadeType.ALL)
    private MaturityModel maturityModel;

}
