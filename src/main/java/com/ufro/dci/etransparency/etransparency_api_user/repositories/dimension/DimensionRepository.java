package com.ufro.dci.etransparency.etransparency_api_user.repositories.dimension;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.dimension.Dimension;
import com.ufro.dci.etransparency.etransparency_api_user.models.maturity.MaturityModel;

@Repository
public interface DimensionRepository extends JpaRepository<Dimension, Long>{
    List<Dimension> findByMaturityModel(MaturityModel maturityModel);
}
