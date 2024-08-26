package com.ufro.dci.etransparency.etransparency_api_user.repositories.recommendation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.recommendation.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByDimensionAndCurrentLevel(String dimension, Long currentLevel);
}
