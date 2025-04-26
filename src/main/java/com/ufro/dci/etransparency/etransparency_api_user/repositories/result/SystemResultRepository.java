package com.ufro.dci.etransparency.etransparency_api_user.repositories.result;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufro.dci.etransparency.etransparency_api_user.models.result.SystemResult;

public interface SystemResultRepository extends JpaRepository<SystemResult, Long> {
    Optional<SystemResult> findTopByOrderByIdDesc();
}
