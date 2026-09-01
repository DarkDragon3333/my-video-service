package com.kino.my_video_service.dto.movie;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ChangeReleaseDateRequest {
    @NotNull
    private LocalDate releaseDate;
}
