package com.kino.my_video_service.controller;

import com.kino.my_video_service.dto.movie.ChangeDescriptionRequest;
import com.kino.my_video_service.dto.movie.ChangeTitleRequest;
import com.kino.my_video_service.dto.movie.CreateMovieRequest;
import com.kino.my_video_service.dto.movie.MovieResponse;
import com.kino.my_video_service.entities.MovieEntity;
import com.kino.my_video_service.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

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
                durationToMinutes(movieEntity.getDuration()), movieEntity.getRating()
        );
    }

    @GetMapping("/{id}")
    public MovieResponse getMovie(@PathVariable Long id){
        MovieEntity movieEntity = movieService.findMovieById(id);

        return new MovieResponse(
                movieEntity.getId(), movieEntity.getTitle(), movieEntity.getRequiredPlan(),
                movieEntity.getGenre(), movieEntity.getDescription(), movieEntity.getReleaseDate(),
                durationToMinutes(movieEntity.getDuration()), movieEntity.getRating()
        );
    }

    @GetMapping
    public List<MovieResponse> getAll(){
        List<MovieEntity> movieEntityList = movieService.findAll();

        return movieEntityList.stream().map(
                movie ->
                        new MovieResponse(
                                movie.getId(), movie.getTitle(), movie.getRequiredPlan(),
                                movie.getGenre(), movie.getDescription(), movie.getReleaseDate(),
                                durationToMinutes(movie.getDuration()), movie.getRating()
                        )
        ).toList();
    }

    @PatchMapping("/title/{id}")
    public MovieResponse patchTitle(@PathVariable Long id, @RequestBody @Valid ChangeTitleRequest changeTitleRequest){
        MovieEntity movieEntity = movieService.patchTitle(id, changeTitleRequest.getTitle());

        return new MovieResponse(
                movieEntity.getId(), movieEntity.getTitle(), movieEntity.getRequiredPlan(),
                movieEntity.getGenre(), movieEntity.getDescription(), movieEntity.getReleaseDate(),
                durationToMinutes(movieEntity.getDuration()), movieEntity.getRating()
        );
    }

    @PatchMapping("/description/{id}")
    public MovieResponse patchDescription(@PathVariable Long id, @RequestBody @Valid ChangeDescriptionRequest description){
        MovieEntity movieEntity = movieService.patchDescription(id, description.getDescription());

        return new MovieResponse(
                movieEntity.getId(), movieEntity.getTitle(), movieEntity.getRequiredPlan(),
                movieEntity.getGenre(), movieEntity.getDescription(), movieEntity.getReleaseDate(),
                durationToMinutes(movieEntity.getDuration()), movieEntity.getRating()
        );
    }

    private Integer durationToMinutes(Duration duration){
        return Math.toIntExact(duration.toMinutes());
    }
}
