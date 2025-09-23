package com.pm.noidea.trackingservice.aggregates;

import com.pm.noidea.common.movie.commands.MovieAddCommand;
import com.pm.noidea.common.movie.commands.ViewRegisterCommand;
import com.pm.noidea.common.movie.events.MovieAddedEvent;
import com.pm.noidea.common.movie.events.ViewRegisteredEvent;
import com.pm.noidea.trackingservice.service.UserService;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigInteger;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class MovieAggregate {

    @AggregateIdentifier
    private UUID movieId;
    private BigInteger views;

    @CommandHandler
    public MovieAggregate(MovieAddCommand command) {
        apply(new MovieAddedEvent(command.movieId()));
    }

    @EventSourcingHandler
    public void on(MovieAddedEvent event) {
        this.movieId = event.getId();
        this.views = BigInteger.ZERO;
    }

    @CommandHandler
    public void handle(ViewRegisterCommand command) {
        apply(new ViewRegisteredEvent(command.movieId()));
    }

    @EventSourcingHandler
    public void on(ViewRegisteredEvent event) {
        this.views = this.views.add(BigInteger.ONE);
    }
}
