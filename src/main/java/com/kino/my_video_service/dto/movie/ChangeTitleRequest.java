package com.kino.my_video_service.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChangeTitleRequest {
    @Size(max = 100)
    @NotBlank
    private String title;
}
