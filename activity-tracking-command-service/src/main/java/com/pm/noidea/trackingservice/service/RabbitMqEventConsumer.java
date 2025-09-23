package com.pm.noidea.trackingservice.service;

import com.pm.noidea.common.movie.commands.MovieAddCommand;
import com.pm.noidea.common.movie.messages.MovieAddedMessage;
import com.pm.noidea.common.user.messages.UserVerifiedMessage;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqEventConsumer {

    private final UserService userService;
    private final CommandGateway commandGateway;

    @RabbitListener(queues = "#{rabbitMqProperties.getUserVerifiedEventTopic()}")
    public void processVerifiedEvent(UserVerifiedMessage message) {
        userService.saveUser(message.getUserId());
    }

    @RabbitListener(queues = "#{rabbitMqProperties.getMovieAddedEventTopic()}")
    public void processMovieAddedEvent(MovieAddedMessage message) {
        System.out.println(message);

        commandGateway.send(new MovieAddCommand(message.getId()));
    }
}
