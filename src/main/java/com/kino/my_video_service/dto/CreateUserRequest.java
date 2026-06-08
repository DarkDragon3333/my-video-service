package com.kino.my_video_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    private String login;
    private String password;
    private String displayName;
}
