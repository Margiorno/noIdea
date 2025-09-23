package com.pm.noidea.trackingservice.service;

import com.pm.noidea.common.movie.commands.ViewRegisterCommand;
import com.pm.noidea.trackingservice.dto.RegisterViewInput;
import com.pm.noidea.trackingservice.dto.RegisterViewOutput;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MovieActivityCommandService {

    private final UserService userService;
    private final CommandGateway commandGateway;

    public CompletableFuture<RegisterViewOutput> registerVideoView(RegisterViewInput input) {
        UUID userId;
        UUID movieId;

        try {
            userId = UUID.fromString(input.getUserId());
            movieId = UUID.fromString(input.getMovieId());
        } catch (IllegalArgumentException e) {
            return CompletableFuture.completedFuture(
                    new RegisterViewOutput(false, "Invalid id format")
            );
        }

        if (!userService.existsByUserId(userId))
            return CompletableFuture.completedFuture(
                    new RegisterViewOutput(false, "User not found")
            );

        ViewRegisterCommand command = new ViewRegisterCommand(movieId, userId);
        return commandGateway.send(command)
                .thenApply(result -> new RegisterViewOutput(true, "Command sent successfully"));
    }
}
