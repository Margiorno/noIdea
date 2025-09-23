package com.pm.noidea.moviecatologservice.service;

import com.pm.noidea.common.movie.messages.MovieAddedMessage;
import com.pm.noidea.moviecatologservice.config.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RabbitEventSenderService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;

    public void sendMovieAddedEvent(UUID movieId) {
        MovieAddedMessage event =
                new MovieAddedMessage(movieId);

        rabbitTemplate.convertAndSend(
                rabbitMqProperties.getExchangeName(),
                rabbitMqProperties.getMovieAddedEventRoutingKey(),
                event
        );
    }
}
