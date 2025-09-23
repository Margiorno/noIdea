package trackingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trackingservice.dto.GetMovieStatsOutput;
import trackingservice.dto.MovieStatsOutput;
import trackingservice.repository.MovieStatsRepository;
import trackingservice.views.MovieStatsView;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieStatsService {

    private final MovieStatsRepository movieStatsRepository;

    public GetMovieStatsOutput getMovieStatsById(String id) {
        UUID movieId;

        try {
            movieId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return new GetMovieStatsOutput(false, "Invalid id format", null);
        }

        Optional<MovieStatsView> fromDb = movieStatsRepository.findById(movieId);

        if (fromDb.isEmpty())
            return new GetMovieStatsOutput(false, "Movie with this id does not exists", null);

        MovieStatsView movieStatsView = fromDb.get();

        return new GetMovieStatsOutput(
                true,
                "Success",
                new MovieStatsOutput(id, movieStatsView.getMovieViews().intValue()));
    }

    public List<MovieStatsOutput> getAllMovieStats() {
        return movieStatsRepository.findAll().stream().map(
                movieStatsView -> new MovieStatsOutput(
                        movieStatsView.getMovieId().toString(),
                        movieStatsView.getMovieViews().intValue()))
                .toList();
    }

    public void initMovieStats(UUID movieId) {
        MovieStatsView movie = MovieStatsView.builder()
                .movieId(movieId)
                .movieViews(BigInteger.ZERO)
                .build();

        movieStatsRepository.save(movie);
    }

    @Transactional
    public void incrementMovieView(UUID movieId) {
        movieStatsRepository.incrementViews(movieId);
    }
}
