package com.ufro.dci.etransparency.etransparency_api_user.repositories.level;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.level.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long>{
    
}
