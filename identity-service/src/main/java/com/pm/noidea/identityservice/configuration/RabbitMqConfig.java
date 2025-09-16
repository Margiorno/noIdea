package com.pm.noidea.identityservice.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String REGISTERED_EVENT_TOPIC = "registered-queue";
    public static final String EXCHANGE = "notification-exchange";
    public static final String REGISTER_EVENT_ROUTING_KEY = "registered-event";

    @Bean
    public Queue registeredEventQueue() {
        return new Queue(REGISTERED_EVENT_TOPIC);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding registeredEventBinding(
            @Qualifier("registeredEventQueue") Queue queue,
            DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(REGISTER_EVENT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
