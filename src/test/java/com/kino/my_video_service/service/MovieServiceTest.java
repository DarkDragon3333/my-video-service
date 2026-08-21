package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.MovieEntity;
import com.kino.my_video_service.enums.Genre;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    public void initObj() {
        this.movieRepository = mock(MovieRepository.class);
        this.movieService = new MovieService(movieRepository);
    }

    @Test
    public void createMovie_SuccessCreation() {
        String title = "Terminator 1";
        SubscriptionPlan requiredPlan = SubscriptionPlan.PREMIUM;
        Genre genre = Genre.ACTION;
        String description = "description";
        LocalDate releaseDate = LocalDate.of(2000, 10, 10);
        Integer durationMinutes = 120;

        movieService.createMovie(
                title, requiredPlan, genre,
                description, releaseDate, durationMinutes
        );

        ArgumentCaptor<MovieEntity> argumentCaptor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(movieRepository, times(1)).save(argumentCaptor.capture());

        MovieEntity movieEntityCapture = argumentCaptor.getValue();

        assertEquals(title, movieEntityCapture.getTitle());
        assertEquals(requiredPlan, movieEntityCapture.getRequiredPlan());
        assertEquals(genre, movieEntityCapture.getGenre());
        assertEquals(description, movieEntityCapture.getDescription());
        assertEquals(releaseDate, movieEntityCapture.getReleaseDate());
        assertEquals(durationMinutes, Math.toIntExact(movieEntityCapture.getDuration().toMinutes()));
    }
}
