package com.pm.noidea.identityservice.controller;

import com.pm.noidea.identityservice.dto.AuthResponseDTO;
import com.pm.noidea.identityservice.dto.LoginRequestDto;
import com.pm.noidea.identityservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Map;

@AllArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @MutationMapping
    public ResponseEntity<AuthResponseDTO> login(@Argument @Valid LoginRequestDto loginRequestDto) {

        AuthResponseDTO response = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        return ResponseEntity.ok().body(response);
    }


}
