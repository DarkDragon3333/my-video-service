package com.kino.my_video_service.dto.movie;

import com.kino.my_video_service.enums.Genre;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeGenreRequest {
    @NotNull
    private Genre genre;
}
