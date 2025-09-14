package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.dto.JwtTokenDTO;
import com.pm.noidea.identityservice.dto.LoginResponseDTO;
import com.pm.noidea.identityservice.dto.RegisterResponseDTO;
import com.pm.noidea.identityservice.exception.InvalidCredentialsException;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final JwtService jwtService;
    private final UserTokenService userTokenService;
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(String email, String password) {

        return authRepository.findByEmail(email)
                .map(authUser -> {
                    if (!passwordEncoder.matches(password, authUser.getHashedPassword())) {
                        return new LoginResponseDTO(false, "Invalid credentials", null, null);
                    }
                    if (!authUser.isVerified()) {
                        return new LoginResponseDTO(false, "User is not verified", null, null);
                    }
                    JwtTokenDTO token = jwtService.generateToken(authUser.getId());
                    return new LoginResponseDTO(true, "Login success", token.token(), token.expiresAt());
                })
                .orElse(new LoginResponseDTO(false, "Invalid credentials", null, null));
    }

    public RegisterResponseDTO register(String email, String password) {

        if (authRepository.existsByEmail(email))
            return new RegisterResponseDTO(false, "User with this email already exists");

        String hashedPassword = passwordEncoder.encode(password);
        AuthUser authUser = AuthUser.builder().email(email).hashedPassword(hashedPassword).build();
        AuthUser savedUser = authRepository.save(authUser);




        //TODO more data (communication with other microservice with profile) & communication with email microservice to generate email verification code

        return new RegisterResponseDTO(true, "Successfully registered");
    }
}
