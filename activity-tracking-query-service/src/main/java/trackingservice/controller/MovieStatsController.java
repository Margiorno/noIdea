package trackingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import trackingservice.dto.GetMovieStatsInput;
import trackingservice.dto.GetMovieStatsOutput;
import trackingservice.dto.MovieStatsOutput;
import trackingservice.service.MovieStatsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieStatsController {


    private final MovieStatsService movieStatsService;

    @QueryMapping
    public List<MovieStatsOutput> getStatsAllMovies(){
        return movieStatsService.getAllMovieStats();
    }

    @MutationMapping
    public GetMovieStatsOutput getMovieById(@Argument @Valid GetMovieStatsInput input){
        return movieStatsService.getMovieStatsById(input.getMovieId());
    }
}
