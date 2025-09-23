package com.pm.noidea.identityservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMqProperties {
    private String exchangeName;

    private String registeredEventRoutingKey;
    private String verifiedEventRoutingKey;
}
