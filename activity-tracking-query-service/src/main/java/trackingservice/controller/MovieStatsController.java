package trackingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.federation.EntityMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import trackingservice.dto.GetMovieStatsInput;
import trackingservice.dto.GetMovieStatsOutput;
import trackingservice.dto.MovieDto;
import trackingservice.dto.MovieStatsOutput;
import trackingservice.service.MovieStatsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieStatsController {


    private final MovieStatsService movieStatsService;

//    @QueryMapping
//    public List<MovieStatsOutput> getStatsAllMovies(){
//        return movieStatsService.getAllMovieStats();
//    }
//
//    @QueryMapping
//    public GetMovieStatsOutput getMovieById(@Argument @Valid GetMovieStatsInput input){
//        return movieStatsService.getMovieStatsById(input.getMovieId());
//    }

    @EntityMapping
    public MovieDto movie(String id) {
        return new MovieDto(id);
    }

    @SchemaMapping(typeName = "Movie", field = "viewsCounter")
    public int getViewsCounter(MovieDto movie) {
        return movieStatsService.getMovieStatsById(movie.id()).stats().viewsCounter();
    }
}
