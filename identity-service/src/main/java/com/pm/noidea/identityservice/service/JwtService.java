package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.dto.LoginResponseDTO;
import com.pm.noidea.identityservice.util.TimeFormatter;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private final TimeFormatter timeFormatter;


    public LoginResponseDTO generateToken(UUID id) {
        Instant now = timeFormatter.now();
        Instant expiration = timeFormatter.plusMillis(now, jwtExpiration);

        String token = Jwts.builder()
                .subject(id.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSignInKey())
                .compact();

        String formattedExpiration = timeFormatter.formatInstant(expiration);
        return new LoginResponseDTO(token, formattedExpiration);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
