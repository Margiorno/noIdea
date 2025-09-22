package com.pm.noidea.moviecatologservice.service;

import com.pm.noidea.moviecatologservice.dto.MovieCreationResponseDTO;
import com.pm.noidea.moviecatologservice.dto.MovieRequestDTO;
import com.pm.noidea.moviecatologservice.dto.MovieResponseDTO;
import com.pm.noidea.moviecatologservice.model.Movie;
import com.pm.noidea.moviecatologservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    public final MovieRepository repository;
    private final RabbitEventSenderService rabbitEventSenderService;

    public MovieCreationResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {

        if (repository.existsByTitle(movieRequestDTO.getTitle()))
            return new MovieCreationResponseDTO(
                    false,
                    "Movie with this title already exists",
                    null,null);

        Movie movie = Movie.builder()
                .title(movieRequestDTO.getTitle())
                .build();

        Movie savedMovie = repository.save(movie);

        rabbitEventSenderService.sendMovieAddedEvent(savedMovie.getId());

        return new MovieCreationResponseDTO(true,
                "Successfully created",
                savedMovie.getId().toString(),
                savedMovie.getTitle());
    }

    public List<MovieResponseDTO> getAllMovies() {
        return repository.findAll().stream().map(
                movie -> new MovieResponseDTO(
                        movie.getId().toString(), movie.getTitle())).toList();
    }


}
