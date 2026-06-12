package com.kino.my_video_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(Long id) {
        super("Wrong password. Id: " + id);
    }
}
