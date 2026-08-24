package com.kino.my_video_service.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeDescriptionRequest {
    @Size(max = 2000)
    @NotBlank
    private String description;
}
