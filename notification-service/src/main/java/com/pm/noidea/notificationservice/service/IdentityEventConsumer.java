package com.pm.noidea.notificationservice.service;

import com.pm.noidea.notificationservice.configuration.RabbitMqConfig;
import com.pm.noidea.notificationservice.dto.RegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class IdentityEventConsumer {

    private final EmailService emailService;

    public IdentityEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMqConfig.REGISTERED_EVENT_TOPIC)
    public void processRegisteredEvent(RegisteredEvent event) {
        emailService.sendVerificationCode(event.getEmail(), event.getCode());
    }
}
