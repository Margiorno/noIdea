package com.pm.noidea.identityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record AuthResponseDTO(String token, LocalDateTime expiresAt){}

