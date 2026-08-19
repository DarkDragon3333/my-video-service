package com.kino.my_video_service.dto.movie;

import com.kino.my_video_service.enums.Genre;
import com.kino.my_video_service.enums.SubscriptionPlan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class CreateMovieRequest {

    @Size(max = 100)
    @NotBlank
    private String title;

    @NotNull
    private SubscriptionPlan requiredPlan;

    @NotNull
    private Genre genre;

    @Size(max = 2000)
    @NotBlank
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Positive
    @NotNull
    private Integer durationMinutes;

}
