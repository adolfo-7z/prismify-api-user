package com.ufro.dci.etransparency.etransparency_api_user.user.application.usecases;

import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.in.DeleteUserUseCase;
import com.ufro.dci.etransparency.etransparency_api_user.user.domain.ports.out.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase{

    private final UserRepository userRepository;

    @Override
    public String deleteUser(Long id){
        boolean deleted = userRepository.deleteById(id);
        return deleted ? "User deleted" : "User with the present ID was not found";
    }
    
}
