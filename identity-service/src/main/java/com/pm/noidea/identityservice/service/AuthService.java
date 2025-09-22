package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.configuration.RabbitMqProperties;
import com.pm.noidea.identityservice.dto.JwtTokenDTO;
import com.pm.noidea.identityservice.dto.LoginResponseDTO;
import com.pm.noidea.identityservice.dto.RegisterResponseDTO;
import com.pm.noidea.identityservice.dto.VerificationResponseDTO;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final JwtService jwtService;
    private final UserTokenService userTokenService;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;
    private final RabbitEventSenderService rabbitEventSenderService;
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

        String code = userTokenService.generateVerificationCode(savedUser);
        rabbitEventSenderService.sendUserRegisteredEvent(email, code);

        return new RegisterResponseDTO(true, "Successfully registered");
    }

    public VerificationResponseDTO verifyAccount(String email, String code) {

        return authRepository.findByEmail(email)
                .map(user -> {
                    if (!userTokenService.verifyUser(user, code)) {
                        return new VerificationResponseDTO(false, "Invalid verification code");
                    }

                    user.setVerified(true);
                    authRepository.save(user);

                    rabbitEventSenderService.sendUserVerifiedEvent(user.getId());

                    return new VerificationResponseDTO(true, "Successfully verified");
                })
                .orElse(new VerificationResponseDTO(false, "User not found"));
    }
}
