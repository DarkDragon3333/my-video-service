package com.kino.my_video_service.exception.movie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameTitleException extends RuntimeException {
    public SameTitleException(Long id, String oldTitle, String newTitle) {
        super(
                "New login matches the current one. " +
                        "Id: " + id  +
                        ", old title: " + oldTitle +
                        ", new title: " + newTitle
        );
    }
}
