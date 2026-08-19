package com.kino.my_video_service.dto.movie;

import com.kino.my_video_service.enums.Genre;
import com.kino.my_video_service.enums.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MovieResponse {
    private Long id;

    private String title;

    private SubscriptionPlan requiredPlan;

    private Genre genre;

    private String description;

    private LocalDate releaseDate;

    private Integer durationMinutes;

    private Double rating;
}
