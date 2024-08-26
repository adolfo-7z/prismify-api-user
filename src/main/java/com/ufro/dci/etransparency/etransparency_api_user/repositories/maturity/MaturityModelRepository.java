package com.ufro.dci.etransparency.etransparency_api_user.repositories.maturity;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.maturity.MaturityModel;

@Repository
public interface MaturityModelRepository extends JpaRepository<MaturityModel, Long> {
    Optional<MaturityModel> findByIsActiveTrue();
}
