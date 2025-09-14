package com.pm.noidea.identityservice.dto;

public record LoginResponseDTO(boolean success, String message, String token, String expiresAt){}

