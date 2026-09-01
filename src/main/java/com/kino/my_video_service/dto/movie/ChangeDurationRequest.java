package com.kino.my_video_service.dto.movie;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeDurationRequest {
    @Positive
    @NotNull
    private Integer durationMinutes;
}
