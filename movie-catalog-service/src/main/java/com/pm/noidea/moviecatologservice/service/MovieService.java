package com.pm.noidea.moviecatologservice.service;

import com.pm.noidea.moviecatologservice.dto.MovieCreationResponseDTO;
import com.pm.noidea.moviecatologservice.dto.MovieRequestDTO;
import com.pm.noidea.moviecatologservice.dto.MovieResponseDTO;
import com.pm.noidea.moviecatologservice.model.Movie;
import com.pm.noidea.moviecatologservice.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public MovieResponseDTO getMovie(String movieId) {
        UUID movieUuid;
        try {
            movieUuid = UUID.fromString(movieId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid movie ID format: " + movieId, e);
        }

        Movie movie = repository.findById(movieUuid)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + movieId));

        return new MovieResponseDTO(movie.getId().toString(), movie.getTitle());
    }


}
