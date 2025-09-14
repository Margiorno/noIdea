package com.pm.noidea.identityservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "application.action-token")
public class ActionTokenProperties {
    private Duration verificationExpiration;
}
