package com.pm.noidea.moviecatologservice.controller;

import com.pm.noidea.moviecatologservice.dto.MovieCreationResponseDTO;
import com.pm.noidea.moviecatologservice.dto.MovieRequestDTO;
import com.pm.noidea.moviecatologservice.dto.MovieResponseDTO;
import com.pm.noidea.moviecatologservice.service.MovieService;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.federation.EntityMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MovieCatalogController {

    public final MovieService service;

    @MutationMapping
    public MovieCreationResponseDTO addMovie(@Argument @Valid MovieRequestDTO movieRequest) {
        return service.createMovie(movieRequest);
    }

    @QueryMapping
    public List<MovieResponseDTO> movies() {
        return service.getAllMovies();
    }

    @QueryMapping
    public MovieResponseDTO movie(@Argument String id) {
        return service.getMovie(id);
    }

    @EntityMapping
    public MovieResponseDTO movie(Map<String, Object> representation) {
        String movieId = representation.get("id").toString();
        return service.getMovie(movieId);
    }
}
