package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.dto.AuthResponseDTO;
import com.pm.noidea.identityservice.exception.EmailAlreadyExistsException;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthResponseDTO login(String email, String password) {

        if (authRepository.existsByEmail(email))
            throw new EmailAlreadyExistsException("Email already exists: %s".formatted(email));

        return null;
    }
}
