package com.kino.my_video_service.controller;

import com.kino.my_video_service.dto.movie.CreateMovieRequest;
import com.kino.my_video_service.dto.movie.MovieResponse;
import com.kino.my_video_service.entities.MovieEntity;
import com.kino.my_video_service.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse createMovie(@RequestBody @Valid CreateMovieRequest createMovieRequest){
        MovieEntity movieEntity = movieService.createMovie(
                createMovieRequest.getTitle(),
                createMovieRequest.getRequiredPlan(),
                createMovieRequest.getGenre(),
                createMovieRequest.getDescription(),
                createMovieRequest.getReleaseDate(),
                createMovieRequest.getDurationMinutes()
        );

        return new MovieResponse(
                movieEntity.getId(), movieEntity.getTitle(), movieEntity.getRequiredPlan(),
                movieEntity.getGenre(), movieEntity.getDescription(), movieEntity.getReleaseDate(),
                Math.toIntExact(movieEntity.getDuration().toMinutes()), movieEntity.getRating()
        );
    }
}
