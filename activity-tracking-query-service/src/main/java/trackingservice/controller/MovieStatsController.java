package trackingservice.controller;

import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import trackingservice.dto.GetMovieStatsInput;
import trackingservice.dto.GetMovieStatsOutput;
import trackingservice.dto.MovieStatsOutput;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieStatsController {

    @QueryMapping
    public List<MovieStatsOutput> getStatsAllMovies(){

        return new ArrayList<>(List.of(
                new MovieStatsOutput("id",21312)
        ));
    }

    @MutationMapping
    public GetMovieStatsOutput getMovieById(@Argument @Valid GetMovieStatsInput input){

        return new GetMovieStatsOutput(true, "success", new MovieStatsOutput("id",21312));
    }

}
