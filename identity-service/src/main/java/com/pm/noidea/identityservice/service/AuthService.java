package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;

    public String login(String email, String password) {

        Optional<AuthUser> user = authRepository.findByEmail(email);

        return null;
    }
}
