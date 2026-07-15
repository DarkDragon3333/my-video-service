package com.kino.my_video_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeDisplayNameRequest {
    @Size(max = 50)
    @NotBlank
    private String displayName;
}
