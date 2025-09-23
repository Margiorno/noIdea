package com.pm.noidea.trackingservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RabbitMqConfig {

    private final RabbitMqProperties rabbitMqProperties;

    // USER EVENTS

    @Bean
    public Queue verifiedEventQueue() {
        return new Queue(rabbitMqProperties.getUserVerifiedEventTopic());
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange (rabbitMqProperties.getUserExchangeName());
    }

    @Bean
    public Binding userVerifiedEventBinding(
            @Qualifier("verifiedEventQueue") Queue queue,
            @Qualifier("userExchange") TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(rabbitMqProperties.getUserVerifiedEventRoutingKey());
    }



    // MOVIE EVENTS

    @Bean
    public Queue movieAddedEventQueue() {
        return new Queue(rabbitMqProperties.getMovieAddedEventTopic());
    }

    @Bean
    public TopicExchange movieExchange() {
        return new TopicExchange (rabbitMqProperties.getMovieExchangeName());
    }

    @Bean
    public Binding movieAddedEventBinding(
            @Qualifier("movieAddedEventQueue") Queue queue,
            @Qualifier("movieExchange") TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(rabbitMqProperties.getMovieAddedEventRoutingKey());
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
