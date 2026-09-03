package com.kino.my_video_service.controller;

import com.kino.my_video_service.dto.movie.*;
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

        return toResponse(movieEntity);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovie(@PathVariable Long id){
        MovieEntity movieEntity = movieService.findMovieById(id);
        return toResponse(movieEntity);
    }

    @GetMapping
    public List<MovieResponse> getAll(){
        List<MovieEntity> movieEntityList = movieService.findAll();
        return movieEntityList.stream().map(
                this::toResponse
        ).toList();
    }

    @PatchMapping("/{id}/title")
    public MovieResponse patchTitle(@PathVariable Long id, @RequestBody @Valid ChangeTitleRequest changeTitleRequest){
        MovieEntity movieEntity = movieService.patchTitle(id, changeTitleRequest.getTitle());
        return toResponse(movieEntity);
    }

    @PatchMapping("/{id}/description")
    public MovieResponse patchDescription(@PathVariable Long id, @RequestBody @Valid ChangeDescriptionRequest description){
        MovieEntity movieEntity = movieService.patchDescription(id, description.getDescription());
        return toResponse(movieEntity);
    }

    @PatchMapping("/{id}/required-plan")
    public MovieResponse patchRequiredPlan(@PathVariable Long id, @RequestBody @Valid ChangeRequiredPlanRequest requiredPlanRequest){
        MovieEntity movieEntity = movieService.patchRequiredPlan(id, requiredPlanRequest.getRequiredPlan());
        return toResponse(movieEntity);
    }

    @PatchMapping("/{id}/genre")
    public MovieResponse patchGenre(@PathVariable Long id, @RequestBody @Valid ChangeGenreRequest genreRequest){
        MovieEntity movieEntity = movieService.patchGenre(id, genreRequest.getGenre());
        return toResponse(movieEntity);
    }

    @PatchMapping("/{id}/release-date")
    public MovieResponse patchReleaseDate(@PathVariable Long id, @RequestBody @Valid ChangeReleaseDateRequest releaseDateRequest){
        MovieEntity movieEntity = movieService.patchReleaseDate(id, releaseDateRequest.getReleaseDate());
        return toResponse(movieEntity);
    }

    @PatchMapping("/{id}/duration")
    public MovieResponse patchDuration(@PathVariable Long id, @RequestBody @Valid ChangeDurationRequest changeDurationRequest){
        MovieEntity movieEntity = movieService.patchDuration(id, changeDurationRequest.getDurationMinutes());
        return toResponse(movieEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
    }
    
    private MovieResponse toResponse(MovieEntity movieEntity) {
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
