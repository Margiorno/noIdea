package com.pm.noidea.identityservice;

import com.pm.noidea.identityservice.dto.JwtTokenDTO;
import com.pm.noidea.identityservice.dto.LoginResponseDTO;
import com.pm.noidea.identityservice.dto.RegisterResponseDTO;
import com.pm.noidea.identityservice.dto.VerificationResponseDTO;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.AuthRepository;
import com.pm.noidea.identityservice.service.AuthService;
import com.pm.noidea.identityservice.service.JwtService;
import com.pm.noidea.identityservice.service.RabbitEventSenderService;
import com.pm.noidea.identityservice.service.UserTokenService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock AuthRepository authRepository;
    @Mock JwtService jwtService;
    @Mock UserTokenService userTokenService;
    @Mock PasswordEncoder passwordEncoder;
    @Mock RabbitEventSenderService rabbitEventSenderService;

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

    @Test
    public void register_shouldSucceed_whenEmailIsNotTaken() {
        String password = Instancio.create(String.class);
        AuthUser user = Instancio.create(AuthUser.class);

        when(authRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(user.getHashedPassword());
        when(authRepository.save(any(AuthUser.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        doNothing().when(rabbitEventSenderService).sendUserRegisteredEvent(anyString(), anyString());

        RegisterResponseDTO result = authService.register(user.getEmail(), password);

        assertThat(result).isNotNull();
        assertThat(result.success()).isTrue();
        assertThat(result.message()).isEqualTo("Successfully registered");

        ArgumentCaptor<AuthUser> userCaptor = ArgumentCaptor.forClass(AuthUser.class);
        verify(authRepository).save(userCaptor.capture());

        AuthUser capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
        verify(userTokenService).generateVerificationCode(capturedUser);
    }

    @Test
    public void register_shouldFail_whenEmailIsTaken() {
        String password = Instancio.create(String.class);
        AuthUser user = Instancio.create(AuthUser.class);

        when(authRepository.existsByEmail(user.getEmail())).thenReturn(true);

        RegisterResponseDTO result = authService.register(user.getEmail(), password);

        assertThat(result).isNotNull();
        assertThat(result.success()).isFalse();
        assertThat(result.message()).isEqualTo("User with this email already exists");

        verify(passwordEncoder, never()).encode(anyString());
        verify(authRepository, never()).save(any(AuthUser.class));
        verify(userTokenService, never()).generateVerificationCode(any(AuthUser.class));
    }

    @Test
    public void verifyAccount_shouldSucceed_whenCodeIsValid(){
        String code = Instancio.create(String.class);
        AuthUser unverifiedUser = Instancio.of(AuthUser.class)
                .set(field(AuthUser::isVerified), false)
                .create();

        when(authRepository.findByEmail(unverifiedUser.getEmail())).thenReturn(Optional.of(unverifiedUser));
        when(userTokenService.verifyUser(unverifiedUser, code)).thenReturn(true);
        doNothing().when(rabbitEventSenderService).sendUserVerifiedEvent(any(UUID.class));


        VerificationResponseDTO result = authService.verifyAccount(unverifiedUser.getEmail(), code);

        assertThat(result).isNotNull();
        assertThat(result.success()).isTrue();
        assertThat(result.message()).isEqualTo("Successfully verified");

        ArgumentCaptor<AuthUser> userCaptor = ArgumentCaptor.forClass(AuthUser.class);
        verify(authRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().isVerified()).isTrue();
    }

    @Test
    public void verifyAccount_shouldFail_whenCodeIsInvalid(){
        String code = Instancio.create(String.class);
        AuthUser unverifiedUser = Instancio.of(AuthUser.class)
                .set(field(AuthUser::isVerified), false)
                .create();

        when(authRepository.findByEmail(unverifiedUser.getEmail())).thenReturn(Optional.of(unverifiedUser));
        when(userTokenService.verifyUser(unverifiedUser, code)).thenReturn(false);

        VerificationResponseDTO result = authService.verifyAccount(unverifiedUser.getEmail(), code);

        assertThat(result).isNotNull();
        assertThat(result.success()).isFalse();
        assertThat(result.message()).isEqualTo("Invalid verification code");

        verify(authRepository, never()).save(any(AuthUser.class));
    }
}
