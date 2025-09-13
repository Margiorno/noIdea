package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.dto.AuthResponseDTO;
import com.pm.noidea.identityservice.exception.InvalidCredentialsException;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO login(String email, String password) {

        AuthUser authUser = authRepository.findByEmail(email).orElseThrow(
                () -> new InvalidCredentialsException("User with this email does not exist: %s".formatted(email)));

        if (!passwordEncoder.matches(password, authUser.getHashedPassword()))
            throw new InvalidCredentialsException("Invalid password");

        return jwtService.generateToken(authUser.getId());
    }
}
