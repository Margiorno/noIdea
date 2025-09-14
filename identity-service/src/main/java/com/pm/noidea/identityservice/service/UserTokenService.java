package com.pm.noidea.identityservice.service;

import com.pm.noidea.identityservice.configuration.ActionTokenProperties;
import com.pm.noidea.identityservice.model.ActionToken;
import com.pm.noidea.identityservice.model.ActionType;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.ActionTokenRepository;
import com.pm.noidea.identityservice.util.TimeFormatter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserTokenService {

    private final TimeFormatter timeFormatter;
    private final ActionTokenProperties actionTokenProperties;
    private final ActionTokenRepository actionTokenRepository;

    public void generateAndSendVerificationCode(AuthUser authUser) {
        //CLEANING
        actionTokenRepository.deleteByActionTypeAndUser(ActionType.ACCOUNT_VERIFICATION, authUser);

        String code = generateCode();
        Instant expires = timeFormatter.now().plusMillis(actionTokenProperties.getVerificationExpiration().toMillis());

        ActionToken actionToken = ActionToken.builder()
                .token(code)
                .user(authUser)
                .actionType(ActionType.ACCOUNT_VERIFICATION)
                .expires(expires)
                .build();

        actionTokenRepository.save(actionToken);

        //TODO COMMUNICATION WITH EMAIL MICROSERVICE
    }

    private String generateCode(){
        RandomStringUtils generator = RandomStringUtils.insecure();
        return generator.next(6, 'A', 'Z' + 1, false, false);
    }
}
