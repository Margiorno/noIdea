package com.pm.noidea.identityservice.controller;

import com.pm.noidea.identityservice.dto.LoginResponseDTO;
import com.pm.noidea.identityservice.dto.LoginRequestDTO;
import com.pm.noidea.identityservice.dto.RegisterRequestDTO;
import com.pm.noidea.identityservice.dto.RegisterResponseDTO;
import com.pm.noidea.identityservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @MutationMapping
    public ResponseEntity<LoginResponseDTO> login(@Argument @Valid LoginRequestDTO loginRequestDto) {

        LoginResponseDTO response = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        return ResponseEntity.ok().body(response);
    }

    @MutationMapping
    public ResponseEntity<RegisterResponseDTO> register(@Argument @Valid RegisterRequestDTO registerRequestDto) {

        RegisterResponseDTO responseDTO = authService.register(registerRequestDto.getEmail(), registerRequestDto.getPassword());

        return ResponseEntity.ok().body(responseDTO);
    }


}
