package com.pm.noidea.moviecatologservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMqProperties {
    private String exchangeName;

    private String movieAddedEventRoutingKey;
}