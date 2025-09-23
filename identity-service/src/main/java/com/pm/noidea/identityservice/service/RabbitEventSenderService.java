package com.pm.noidea.identityservice.service;

import com.pm.noidea.common.user.messages.UserRegisteredMessage;
import com.pm.noidea.common.user.messages.UserVerifiedMessage;
import com.pm.noidea.identityservice.configuration.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RabbitEventSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public void sendUserRegisteredEvent(String email, String code) {
        UserRegisteredMessage event =
                new UserRegisteredMessage(email, code);

        rabbitTemplate.convertAndSend(
                rabbitMqProperties.getExchangeName(),
                rabbitMqProperties.getRegisteredEventRoutingKey(),
                event
        );
    }

    public void sendUserVerifiedEvent(UUID userId){
        UserVerifiedMessage event = new UserVerifiedMessage(userId);

        rabbitTemplate.convertAndSend(
                rabbitMqProperties.getExchangeName(),
                rabbitMqProperties.getVerifiedEventRoutingKey(),
                event
        );
    }
}
