package com.ufro.dci.etransparency.etransparency_api_user.models.manager;

import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.models.institution.Institution;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_manager_username", columnList = "username"),
        @Index(name = "idx_manager_email", columnList = "email")
})
public class Manager extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100)
    private String position;

    @NotNull
    @Column(length = 10)
    private String rut;

    @OneToOne(mappedBy = "manager")
    private Institution institution;
}
