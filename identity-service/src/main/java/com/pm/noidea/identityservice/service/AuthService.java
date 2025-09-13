package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.dto.AuthResponseDTO;
import com.pm.noidea.identityservice.exception.EmailAlreadyExistsException;
import com.pm.noidea.identityservice.exception.UserNotFoundException;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthResponseDTO login(String email, String password) {

        AuthUser authUser = authRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User with this email does not exist: %s".formatted(email)));

        return null;
    }
}
