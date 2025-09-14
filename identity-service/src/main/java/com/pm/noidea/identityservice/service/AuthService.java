package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.dto.LoginResponseDTO;
import com.pm.noidea.identityservice.dto.RegisterResponseDTO;
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

    public LoginResponseDTO login(String email, String password) {

        AuthUser authUser = authRepository.findByEmail(email).orElseThrow(
                () -> new InvalidCredentialsException("User with this email does not exist: %s".formatted(email)));

        if (!passwordEncoder.matches(password, authUser.getHashedPassword()))
            throw new InvalidCredentialsException("Invalid password");

        return jwtService.generateToken(authUser.getId());
    }

    public RegisterResponseDTO register(String email, String password) {

        if (authRepository.existsByEmail(email))
            throw new InvalidCredentialsException("User with this email already exists");

        String hashedPassword = passwordEncoder.encode(password);
        AuthUser authUser = AuthUser.builder().email(email).hashedPassword(hashedPassword).build();

        //TODO more data (communication with other microservice with profile) & communication with email microservice to generate email verification code

        AuthUser saved = authRepository.save(authUser);

        return new RegisterResponseDTO(saved.getId().toString());
    }
}
