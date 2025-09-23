package com.pm.noidea.trackingservice.dto;

public record RegisterViewOutput(
        boolean success,
        String message,
        Integer viewsCount,
        String registeredAt){}

