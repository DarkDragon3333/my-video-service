package com.kino.my_video_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ExceptionHandlerResponse {
    private ZonedDateTime timestamp;
    private Integer status;
    private String message;
    private String path;
}
