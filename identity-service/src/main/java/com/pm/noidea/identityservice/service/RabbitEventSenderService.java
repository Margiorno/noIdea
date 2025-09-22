package com.pm.noidea.identityservice.service;

import com.pm.noidea.common.user.events.UserRegisteredEvent;
import com.pm.noidea.common.user.events.UserVerifiedEvent;
import com.pm.noidea.identityservice.configuration.RabbitMqProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitEventSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public RabbitEventSenderService(RabbitTemplate rabbitTemplate, RabbitMqProperties rabbitMqProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqProperties = rabbitMqProperties;
    }

    public void sendUserRegisteredEvent(String email, String code) {
        UserRegisteredEvent event =
                new UserRegisteredEvent(email, code);

        rabbitTemplate.convertAndSend(
                rabbitMqProperties.getExchangeName(),
                rabbitMqProperties.getRegisteredEventRoutingKey(),
                event
        );
    }

    public void sendUserVerifiedEvent(UUID userId){
        UserVerifiedEvent event = new UserVerifiedEvent(userId);

        rabbitTemplate.convertAndSend(
                rabbitMqProperties.getExchangeName(),
                rabbitMqProperties.getVerifiedEventRoutingKey(),
                event
        );
    }
}
