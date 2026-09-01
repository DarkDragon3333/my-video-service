package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.MovieEntity;
import com.kino.my_video_service.enums.Genre;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.exception.movie.MovieNotFoundException;
import com.kino.my_video_service.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void findMovieById_MovieNotFoundException(){
        assertThrows(MovieNotFoundException.class, () -> movieService.findMovieById(0L));
    }

    @Test
    public void findMovieById_SuccessFind(){
        Long id = 1L;
        MovieEntity testMovieEntity = new MovieEntity();

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));
        assertEquals(testMovieEntity, movieService.findMovieById(id));
    }

    @Test
    public void patchTitle_SameDataFromClient(){
        Long id = 1L;
        String testNewTitle = "newTitle";
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setTitle(testNewTitle);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        MovieEntity movieEntityFromMethod = movieService.patchTitle(id, testNewTitle);

        verify(movieRepository, never()).save(any());
        assertEquals(testMovieEntity, movieEntityFromMethod);
        assertEquals(testMovieEntity.getTitle(), movieEntityFromMethod.getTitle());
    }

    @Test
    public void patchTitle_SuccessPatch(){
        Long id = 1L;
        String newTitle1 = "newTitle1";
        String newTitle2 = "newTitle2";
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setTitle(newTitle1);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        movieService.patchTitle(id, newTitle2);

        ArgumentCaptor<MovieEntity> captor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(movieRepository, times(1)).save(captor.capture());

        MovieEntity movieEntityFromCaptor = captor.getValue();
        assertEquals(testMovieEntity, movieEntityFromCaptor);
        assertEquals(newTitle2, movieEntityFromCaptor.getTitle());
    }

    @Test
    public void patchDescription_SameDataFromClient(){
        Long id = 1L;
        String testNewDescription = "newDescription";
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setDescription(testNewDescription);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        MovieEntity movieEntityFromMethod = movieService.patchDescription(id, testNewDescription);

        verify(movieRepository, never()).save(any());
        assertEquals(testMovieEntity, movieEntityFromMethod);
        assertEquals(testMovieEntity.getDescription(), movieEntityFromMethod.getDescription());
    }

    @Test
    public void patchDescription_SuccessPatch(){
        Long id = 1L;
        String newDescription1 = "newDescription1";
        String newDescription2 = "newDescription2";
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setDescription(newDescription1);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        movieService.patchDescription(id, newDescription2);

        ArgumentCaptor<MovieEntity> captor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(movieRepository, times(1)).save(captor.capture());

        MovieEntity movieEntityFromCaptor = captor.getValue();
        assertEquals(testMovieEntity, movieEntityFromCaptor);
        assertEquals(newDescription2, movieEntityFromCaptor.getDescription());
    }
}
