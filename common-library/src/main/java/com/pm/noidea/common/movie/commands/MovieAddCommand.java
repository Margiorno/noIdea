package com.pm.noidea.common.movie.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record MovieAddCommand(
        @TargetAggregateIdentifier UUID movieId
){}
