package com.pm.noidea.identityservice;

import com.pm.noidea.identityservice.dto.JwtTokenDTO;
import com.pm.noidea.identityservice.dto.LoginResponseDTO;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import com.pm.noidea.identityservice.service.AuthService;
import com.pm.noidea.identityservice.service.JwtService;
import com.pm.noidea.identityservice.service.UserTokenService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock AuthRepository authRepository;
    @Mock JwtService jwtService;
    @Mock UserTokenService userTokenService;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthService authService;

    @Test
    public void login_shouldReturnToken_whenCredentialsAreCorrect(){

        AuthUser verifiedUser = Instancio.of(AuthUser.class)
                .set(field(AuthUser::isVerified), true)
                .create();
        when(authRepository.findByEmail(verifiedUser.getEmail()))
                .thenReturn(Optional.of(verifiedUser));
        when(passwordEncoder.matches(anyString(),anyString()))
                .thenReturn(true);
        JwtTokenDTO token = Instancio.create(JwtTokenDTO.class);
        when(jwtService.generateToken(verifiedUser.getId())).thenReturn(token);

        LoginResponseDTO result = authService.login(verifiedUser.getEmail(), anyString());

        assertThat(result).isNotNull();
        assertThat(result.success()).isTrue();
        assertThat(result.message()).isEqualTo("Login success");
        assertThat(result.token()).isEqualTo(token.token());
        assertThat(result.expiresAt()).isEqualTo(token.expiresAt());
    }

    @Test
    public void login_shouldReturnInvalidCredentials_whenEmailIsIncorrect(){
        AuthUser user = Instancio.create(AuthUser.class);
        when(authRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        LoginResponseDTO result = authService.login(user.getEmail(), anyString());

        assertThat(result).isNotNull();
        assertThat(result.success()).isFalse();
        assertThat(result.message()).isEqualTo("Invalid credentials");
    }

    @Test
    public void login_shouldReturnInvalidCredentials_whenPasswordIsIncorrect(){
        AuthUser user = Instancio.create(AuthUser.class);
        when(authRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(),anyString()))
                .thenReturn(false);

        LoginResponseDTO result = authService.login(user.getEmail(), anyString());

        assertThat(result).isNotNull();
        assertThat(result.success()).isFalse();
        assertThat(result.message()).isEqualTo("Invalid credentials");
    }

    @Test
    public void login_shouldReturnNotVerified_whenUserIsNotVerified(){
        AuthUser unverifiedUser = Instancio.of(AuthUser.class)
                .set(field(AuthUser::isVerified), false)
                .create();
        when(authRepository.findByEmail(unverifiedUser.getEmail()))
                .thenReturn(Optional.of(unverifiedUser));
        when(passwordEncoder.matches(anyString(),anyString()))
                .thenReturn(true);

        LoginResponseDTO result = authService.login(unverifiedUser.getEmail(), anyString());

        assertThat(result).isNotNull();
        assertThat(result.success()).isFalse();
        assertThat(result.message()).isEqualTo("User is not verified");
    }
}
