package com.pm.noidea.trackingservice.controller;

import com.google.type.DateTime;
import com.pm.noidea.trackingservice.dto.RegisterViewInput;
import com.pm.noidea.trackingservice.dto.RegisterViewOutput;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Controller
public class MovieActivityCommandController {

    @MutationMapping
    public RegisterViewOutput registerVideoView(@Argument @Valid RegisterViewInput input) {

        return new RegisterViewOutput(true, "działa",1, LocalDateTime.now().toString());
    }


}
