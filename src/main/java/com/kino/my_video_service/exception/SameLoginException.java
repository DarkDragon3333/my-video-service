package com.kino.my_video_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameLoginException extends RuntimeException {
    public SameLoginException(Long id, String oldLogin, String newLogin) {
        super(
                "New login matches the current one. " +
                        "Id:" + id  +
                        ", old login: " + oldLogin +
                        ", newLogin:" + newLogin
        );
    }
}
