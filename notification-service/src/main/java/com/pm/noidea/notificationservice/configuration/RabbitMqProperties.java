package com.pm.noidea.notificationservice.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMqProperties {
    private String exchangeName;
    private String registeredEventTopic;
    private String registeredEventRoutingKey;
}
