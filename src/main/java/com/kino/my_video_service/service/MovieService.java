package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.MovieEntity;
import com.kino.my_video_service.enums.Genre;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.exception.movie.MovieNotFoundException;
import com.kino.my_video_service.exception.movie.SameMovieInformationException;
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

    public MovieEntity findMovieById(Long id){
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    public List<MovieEntity> findAll(){
        return movieRepository.findAll();
    }

    public MovieEntity patchTitle(Long id, String newTitle){
        MovieEntity patchMovieEntity = findMovieById(id);

        if (newTitle.equals(patchMovieEntity.getTitle()))
            throw new SameMovieInformationException(id, "title", patchMovieEntity.getTitle(), newTitle);

        patchMovieEntity.setTitle(newTitle);
        return movieRepository.save(patchMovieEntity);
    }

    public MovieEntity patchDescription(Long id, String newDescription){
        MovieEntity patchMovieEntity = findMovieById(id);

        if (newDescription.equals(patchMovieEntity.getDescription()))
            throw new SameMovieInformationException(
                    id, "description",
                    patchMovieEntity.getDescription(), newDescription
            );

        patchMovieEntity.setDescription(newDescription);
        return movieRepository.save(patchMovieEntity);

    }
}
