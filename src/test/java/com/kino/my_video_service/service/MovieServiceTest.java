package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.MovieEntity;
import com.kino.my_video_service.enums.Genre;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.exception.movie.MovieNotFoundException;
import com.kino.my_video_service.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Duration;
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

    @Test
    public void patchRequiredPlan_SameDataFromClient(){
        Long id = 1L;
        SubscriptionPlan testNewRequiredPlan = SubscriptionPlan.BASE;
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setRequiredPlan(testNewRequiredPlan);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        MovieEntity movieEntityFromMethod = movieService.patchRequiredPlan(id, testNewRequiredPlan);

        verify(movieRepository, never()).save(any());
        assertEquals(testMovieEntity, movieEntityFromMethod);
        assertEquals(testMovieEntity.getRequiredPlan(), movieEntityFromMethod.getRequiredPlan());
    }

    @Test
    public void patchRequiredPlan_SuccessPatch(){
        Long id = 1L;
        SubscriptionPlan newRequiredPlan1 = SubscriptionPlan.BASE;
        SubscriptionPlan newRequiredPlan2 = SubscriptionPlan.PREMIUM;
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setRequiredPlan(newRequiredPlan1);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        movieService.patchRequiredPlan(id, newRequiredPlan2);

        ArgumentCaptor<MovieEntity> captor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(movieRepository, times(1)).save(captor.capture());

        MovieEntity movieEntityFromCaptor = captor.getValue();
        assertEquals(testMovieEntity, movieEntityFromCaptor);
        assertEquals(newRequiredPlan2, movieEntityFromCaptor.getRequiredPlan());
    }

    @Test
    public void patchGenre_SameDataFromClient(){
        Long id = 1L;
        Genre testNewGenre = Genre.ACTION;
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setGenre(testNewGenre);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        MovieEntity movieEntityFromMethod = movieService.patchGenre(id, testNewGenre);

        verify(movieRepository, never()).save(any());
        assertEquals(testMovieEntity, movieEntityFromMethod);
        assertEquals(testMovieEntity.getGenre(), movieEntityFromMethod.getGenre());
    }

    @Test
    public void patchGenre_SuccessPatch(){
        Long id = 1L;
        Genre newGenre1 = Genre.ACTION;
        Genre newGenre2 = Genre.COMEDY;
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setGenre(newGenre1);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        movieService.patchGenre(id, newGenre2);

        ArgumentCaptor<MovieEntity> captor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(movieRepository, times(1)).save(captor.capture());

        MovieEntity movieEntityFromCaptor = captor.getValue();
        assertEquals(testMovieEntity, movieEntityFromCaptor);
        assertEquals(newGenre2, movieEntityFromCaptor.getGenre());
    }

    @Test
    public void patchReleaseDate_SameDataFromClient(){
        Long id = 1L;
        LocalDate testNewReleaseDate = LocalDate.of(1900, 10, 10);
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setReleaseDate(testNewReleaseDate);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        MovieEntity movieEntityFromMethod = movieService.patchReleaseDate(id, testNewReleaseDate);

        verify(movieRepository, never()).save(any());
        assertEquals(testMovieEntity, movieEntityFromMethod);
        assertEquals(testMovieEntity.getReleaseDate(), movieEntityFromMethod.getReleaseDate());
    }

    @Test
    public void patchReleaseDate_SuccessPatch(){
        Long id = 1L;
        LocalDate newReleaseDate1 = LocalDate.of(1900, 10, 10);
        LocalDate newReleaseDate2 = LocalDate.of(2000, 1, 20);
        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setReleaseDate(newReleaseDate1);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        movieService.patchReleaseDate(id, newReleaseDate2);

        ArgumentCaptor<MovieEntity> captor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(movieRepository, times(1)).save(captor.capture());

        MovieEntity movieEntityFromCaptor = captor.getValue();
        assertEquals(testMovieEntity, movieEntityFromCaptor);
        assertEquals(newReleaseDate2, movieEntityFromCaptor.getReleaseDate());
    }

    @Test
    public void patchDuration_SameDataFromClient(){
        Long id = 1L;
        int testNewDurationMinutes = 120;
        Duration testDuration = Duration.ofMinutes(testNewDurationMinutes);

        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setDuration(testDuration);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        MovieEntity movieEntityFromMethod = movieService.patchDuration(id, testNewDurationMinutes);

        verify(movieRepository, never()).save(any());
        assertEquals(testMovieEntity, movieEntityFromMethod);
        assertEquals(testMovieEntity.getDuration(), movieEntityFromMethod.getDuration());
    }

    @Test
    public void patchDuration_SuccessPatch(){
        Long id = 1L;
        int newTestDurationMinutes1 = 120;
        int newTestDurationMinutes2 = 235;
        Duration testDuration1 = Duration.ofMinutes(newTestDurationMinutes1);
        Duration testDuration2 = Duration.ofMinutes(newTestDurationMinutes2);

        MovieEntity testMovieEntity = new MovieEntity();
        testMovieEntity.setDuration(testDuration1);

        when(movieRepository.findById(id)).thenReturn(Optional.of(testMovieEntity));

        movieService.patchDuration(id, newTestDurationMinutes2);

        ArgumentCaptor<MovieEntity> captor = ArgumentCaptor.forClass(MovieEntity.class);
        verify(movieRepository, times(1)).save(captor.capture());

        MovieEntity movieEntityFromCaptor = captor.getValue();
        assertEquals(testMovieEntity, movieEntityFromCaptor);
        assertEquals(testDuration2, movieEntityFromCaptor.getDuration());
    }
}
