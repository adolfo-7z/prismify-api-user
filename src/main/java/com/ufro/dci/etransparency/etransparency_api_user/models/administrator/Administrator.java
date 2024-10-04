package com.ufro.dci.etransparency.etransparency_api_user.models.administrator;

import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_administrator_username", columnList = "username"),
        @Index(name = "idx_administrator_email", columnList = "email")
})
public class Administrator extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
