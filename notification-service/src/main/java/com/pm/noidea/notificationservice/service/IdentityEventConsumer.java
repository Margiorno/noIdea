package com.pm.noidea.notificationservice.service;

import com.pm.noidea.common.dto.RegisteredEvent;
import com.pm.noidea.notificationservice.configuration.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class IdentityEventConsumer {

    private final EmailService emailService;

    public IdentityEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "#{rabbitMqProperties.getRegisteredEventTopic()}")
    public void processRegisteredEvent(RegisteredEvent event) {
        System.out.println("Received: " + event);

        emailService.sendVerificationCode(event.getEmail(), event.getCode());
    }
}
