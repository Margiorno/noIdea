package com.pm.noidea.trackingservice.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMqProperties {
    private String userExchangeName;
    private String userVerifiedEventTopic;
    private String userVerifiedEventRoutingKey;

    private String movieExchangeName;
    private String movieAddedEventTopic;
    private String movieAddedEventRoutingKey;
}
