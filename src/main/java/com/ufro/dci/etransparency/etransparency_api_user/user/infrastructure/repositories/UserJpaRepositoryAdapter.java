package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.repositories;

import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.models.*;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UserNotFoundException;
import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.entities.UserEntity;

@Component
public class UserJpaRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserJpaRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return jpaRepository.findById(id).map(UserEntity::toDomain)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(UserEntity::toDomain).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRole(Role role) {
        return jpaRepository.existsByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return jpaRepository.findAll().stream().map(UserEntity::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllPaged(int page, int size, String date, String name) {
        Sort sort = date.equalsIgnoreCase("asc")
                ? Sort.by("updatedAt").ascending()
                : Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return jpaRepository.findAll(pageable)
                .stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<User> update(User user) {
        return jpaRepository.findById(user.getId()).map(existing -> {
            existing.setUsername(user.getUsername());
            existing.setPassword(user.getPassword());
            existing.setEmail(user.getEmail());
            existing.setPhoneNumber(user.getPhoneNumber());
            existing.setPosition(user.getPosition());
            existing.setRut(user.getRut());
            existing.setCity(user.getCity());
            existing.setColor(user.getColor());
            existing.setAcronym(user.getAcronym());
            existing.setNotifications(new ArrayList<>(user.getNotifications()));
            existing.setAuditsPerformed(user.getAuditsPerformed());
            return Optional.of(jpaRepository.save(existing).toDomain());
        }).orElse(Optional.empty());
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
