package com.pm.noidea.identityservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false )
    @Size(min = 6, max = 20)
    private String hashedPassword;
}
