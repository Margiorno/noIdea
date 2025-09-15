package com.pm.noidea.notificationservice.service;

import com.pm.noidea.notificationservice.configuration.EmailProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;
    private final MessageSource messageSource;

    @Async
    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailProperties.getSenderAddress());
        message.setTo(to);
        message.setSubject(messageSource.getMessage(
                "email.verification.subject", null, Locale.getDefault()
        ));
        message.setText(messageSource.getMessage(
                "email.verification.body", new Object[]{code}, Locale.getDefault()
        ));
        mailSender.send(message);
    }

}
