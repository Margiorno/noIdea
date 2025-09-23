package com.pm.noidea.identityservice.controller;

import com.pm.noidea.identityservice.dto.*;
import com.pm.noidea.identityservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class IdentityController {

    private final AuthService authService;

    @MutationMapping
    public LoginResponseDTO login(@Argument @Valid LoginRequestDTO loginRequest) {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @MutationMapping
    public RegisterResponseDTO register(@Argument @Valid RegisterRequestDTO registerRequest) {
        return authService.register(registerRequest.getEmail(), registerRequest.getPassword());
    }

    @MutationMapping
    public VerificationResponseDTO activate(@Argument @Valid VerificationRequestDTO verificationRequest) {
        return authService.verifyAccount(verificationRequest.getEmail(), verificationRequest.getCode());
    }


}
