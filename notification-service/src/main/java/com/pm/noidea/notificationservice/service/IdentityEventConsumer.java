package com.pm.noidea.notificationservice.service;

import com.pm.noidea.common.user.events.UserRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class IdentityEventConsumer {

    private final EmailService emailService;

    public IdentityEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "#{rabbitMqProperties.getRegisteredEventTopic()}")
    public void processRegisteredEvent(UserRegisteredEvent event) {
        System.out.println("Received: " + event);

        emailService.sendVerificationCode(event.getEmail(), event.getCode());
    }
}
