package com.kino.my_video_service.exception.movie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameMovieInformationException extends RuntimeException {
    public SameMovieInformationException(Long id, String typeInfo, String oldData, String newData) {
        super(
                "New data matches the current one. " +
                "Movie id: " + id  +
                ", type data: " + typeInfo +
                ", old data: " + oldData +
                ", new data: " + newData
        );
    }
}
