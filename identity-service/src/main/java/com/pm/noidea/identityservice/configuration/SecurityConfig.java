package com.pm.noidea.identityservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
                16,   // saltLength
                32,   // hashLength
                1,    // parallelism
                65536, // memory (KB)
                3     // iterations
        );
    }
}
