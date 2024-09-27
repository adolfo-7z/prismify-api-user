package com.ufro.dci.etransparency.etransparency_api_user.models.evidence;

import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.process.Process;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Cacheable
public class Evidence {

    public enum EvidenceStatus {
        NOT_EVALUATED, ACCEPTED, REJECTED, APPEAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(columnDefinition = "TEXT")
    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String fileType;

    @Column(columnDefinition = "TEXT")
    private String conditions;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    private EvidenceStatus evidenceStatus;

    @ManyToOne
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @ManyToOne
    @JoinColumn(name = "process_id", referencedColumnName = "id")
    private Process process;

}
