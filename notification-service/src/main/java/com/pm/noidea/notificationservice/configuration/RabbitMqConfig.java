package com.pm.noidea.notificationservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
public class RabbitMqConfig {

    private final RabbitMqProperties rabbitMqProperties;

    @Bean
    public Queue registeredEventQueue() {
        return new Queue(rabbitMqProperties.getRegisteredEventTopic());
    }

    @Bean
    public TopicExchange  exchange() {
        return new TopicExchange (rabbitMqProperties.getExchangeName());
    }

    @Bean
    public Binding registeredEventBinding(
            @Qualifier("registeredEventQueue") Queue queue,
            TopicExchange  exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(rabbitMqProperties.getRegisteredEventRoutingKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
