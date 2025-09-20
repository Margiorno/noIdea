package com.pm.noidea.identityservice.service;

import com.pm.noidea.common.dto.RegisteredEvent;
import com.pm.noidea.identityservice.configuration.ActionTokenProperties;
import com.pm.noidea.identityservice.configuration.RabbitMqConfig;
import com.pm.noidea.identityservice.configuration.RabbitMqProperties;
import com.pm.noidea.identityservice.model.ActionToken;
import com.pm.noidea.identityservice.model.ActionType;
import com.pm.noidea.identityservice.model.AuthUser;
import com.pm.noidea.identityservice.repository.ActionTokenRepository;
import com.pm.noidea.identityservice.util.TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class UserTokenService {

    private final TimeFormatter timeFormatter;
    private final ActionTokenProperties actionTokenProperties;
    private final ActionTokenRepository actionTokenRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public String generateVerificationCode(AuthUser authUser) {
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

        return code;
    }

    public boolean verifyUser(AuthUser user, String code) {
        return actionTokenRepository
                .findByActionTypeAndUser(ActionType.ACCOUNT_VERIFICATION, user)
                .filter(token -> !token.getExpires().isBefore(Instant.now()))
                .filter(token -> token.getToken().equals(code))
                .map(token -> {
                    actionTokenRepository.delete(token);
                    return true;
                })
                .orElse(false);
    }

    private String generateCode(){
        RandomStringUtils generator = RandomStringUtils.insecure();
        return generator.next(6, 'A', 'Z' + 1, false, false);
    }


}
