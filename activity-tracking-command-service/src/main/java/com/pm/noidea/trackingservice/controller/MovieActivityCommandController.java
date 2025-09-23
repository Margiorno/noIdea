package com.pm.noidea.trackingservice.controller;

import com.pm.noidea.common.movie.commands.ViewRegisterCommand;
import com.pm.noidea.trackingservice.dto.RegisterViewInput;
import com.pm.noidea.trackingservice.dto.RegisterViewOutput;
import com.pm.noidea.trackingservice.service.MovieActivityCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class MovieActivityCommandController {

    private final MovieActivityCommandService movieActivityCommandService;

    @MutationMapping
    public CompletableFuture<RegisterViewOutput> registerVideoView(@Argument @Valid RegisterViewInput input) {

        return movieActivityCommandService.registerVideoView(input);
    }
}
