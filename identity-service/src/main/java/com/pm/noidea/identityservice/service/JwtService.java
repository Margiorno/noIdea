package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.configuration.JwtProperties;
import com.pm.noidea.identityservice.dto.JwtTokenDTO;
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
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final TimeFormatter timeFormatter;


    public JwtTokenDTO generateToken(UUID id) {
        Instant now = timeFormatter.now();
        Instant expiration = now.plusMillis(jwtProperties.getExpiration().toMillis());

        String token = Jwts.builder()
                .subject(id.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(getSignInKey())
                .compact();

        String formattedExpiration = timeFormatter.formatInstant(expiration);
        return new JwtTokenDTO(token, formattedExpiration);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
