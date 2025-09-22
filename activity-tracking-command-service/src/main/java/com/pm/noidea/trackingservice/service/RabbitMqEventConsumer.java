package com.pm.noidea.trackingservice.service;

import com.pm.noidea.common.movie.events.MovieAddedEvent;
import com.pm.noidea.common.user.events.UserVerifiedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqEventConsumer {

    private final UserService userService;

    @RabbitListener(queues = "#{rabbitMqProperties.getUserVerifiedEventTopic()}")
    public void processVerifiedEvent(UserVerifiedEvent event) {
        userService.saveUser(event.getUserId());
    }

    @RabbitListener(queues = "#{rabbitMqProperties.getMovieAddedEventTopic()}")
    public void processMovieAddedEvent(MovieAddedEvent event) {
        System.out.println("Received: " + event);
    }
}
