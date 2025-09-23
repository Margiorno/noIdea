package com.pm.noidea.moviecatologservice.dto;

public record MovieCreationResponseDTO(
        boolean success,
        String message,
        String id,
        String title){}

