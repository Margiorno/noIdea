package com.pm.noidea.identityservice;

import com.pm.noidea.identityservice.configuration.JwtProperties;
import com.pm.noidea.identityservice.dto.JwtTokenDTO;
import com.pm.noidea.identityservice.service.JwtService;
import com.pm.noidea.identityservice.util.TimeFormatter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.instancio.Instancio;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock private JwtProperties jwtProperties;
    @Mock private TimeFormatter timeFormatter;

    @InjectMocks
    private JwtService jwtService;

    @Test
    void generateToken_shouldCreateValidToken_withCorrectClaimsAndExpiration() {

        UUID userId = UUID.randomUUID();
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Duration expiration = Duration.ofMinutes(5);
        Instant expectedExpiration = now.plus(expiration);
        String secretKeyString = Instancio.of(String.class)
                .withSettings(Settings.create()
                        .set(Keys.STRING_MIN_LENGTH, 48)
                        .set(Keys.STRING_MAX_LENGTH, 48))
                .create();

        when(timeFormatter.now()).thenReturn(now);
        when(jwtProperties.getExpiration()).thenReturn(expiration);
        when(jwtProperties.getSecretKey()).thenReturn(secretKeyString);
        when(timeFormatter.formatInstant(any(Instant.class)))
                .thenReturn(Instancio.create(String.class));

        JwtTokenDTO result = jwtService.generateToken(userId);

        assertThat(result).isNotNull();
        assertThat(result.token()).isNotBlank();
        assertThat(result.expiresAt()).isNotBlank();

        SecretKey key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(result.token())
                .getPayload();

        assertThat(claims.getSubject()).isEqualTo(userId.toString());
        assertThat(claims.getIssuedAt()).isEqualTo(Date.from(now));
        assertThat(claims.getExpiration()).isEqualTo(Date.from(expectedExpiration));
    }


}
