package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.MovieEntity;
import com.kino.my_video_service.enums.Genre;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.exception.movie.MovieNotFoundException;
import com.kino.my_video_service.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public MovieEntity createMovie(
            String title, SubscriptionPlan requiredPlan, Genre genre,
            String description, LocalDate releaseDate, Integer durationMinutes
    ){
        Duration duration = Duration.ofMinutes(durationMinutes);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setTitle(title);
        movieEntity.setRequiredPlan(requiredPlan);
        movieEntity.setGenre(genre);
        movieEntity.setDescription(description);
        movieEntity.setReleaseDate(releaseDate);
        movieEntity.setDuration(duration);

        return movieRepository.save(movieEntity);
    }

    public MovieEntity getMovie(Long id){
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    public List<MovieEntity> findAll(){
        return movieRepository.findAll();
    }
}
