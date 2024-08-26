package com.ufro.dci.etransparency.etransparency_api_user.services.auth;

import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ufro.dci.etransparency.etransparency_api_user.config.security.models.CustomUserDetails;
import com.ufro.dci.etransparency.etransparency_api_user.models.UserEntity;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.administrator.AdministratorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.auditor.AuditorRepository;
import com.ufro.dci.etransparency.etransparency_api_user.repositories.manager.ManagerRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio para la carga de detalles de usuario.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

        private final AdministratorRepository administratorRepository;
        private final AuditorRepository auditorRepository;
        private final ManagerRepository managerRepository;

        /**
         * Carga un usuario por su nombre de usuario.
         *
         * @param username nombre de usuario.
         * @return detalles del usuario.
         * @throws UsernameNotFoundException si no se encuentra el usuario con el nombre
         *                                   de usuario especificado.
         */
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity user = findUserByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with username: " + username));
                return new CustomUserDetails(user);
        }

        /**
         * Busca un usuario por su nombre de usuario.
         *
         * @param username nombre de usuario.
         * @return un Optional que contiene el usuario si se encuentra.
         */
        private Optional<UserEntity> findUserByUsername(String username) {
                Optional<? extends UserEntity> user;

                user = administratorRepository.findByUsername(username);
                if (user.isPresent()) {
                        return Optional.of((UserEntity) user.get());
                }

                user = auditorRepository.findByUsername(username);
                if (user.isPresent()) {
                        return Optional.of((UserEntity) user.get());
                }

                user = managerRepository.findByUsername(username);
                if (user.isPresent()) {
                        return Optional.of((UserEntity) user.get());
                }

                return Optional.empty();
        }

}
