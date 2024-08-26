package com.ufro.dci.etransparency.etransparency_api_user.repositories.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufro.dci.etransparency.etransparency_api_user.models.request.InstitutionRequest;

@Repository
public interface InstitutionRequestRepository extends JpaRepository<InstitutionRequest, Long> {
    
}
