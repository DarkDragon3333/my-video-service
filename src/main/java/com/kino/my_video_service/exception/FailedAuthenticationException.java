package com.kino.my_video_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class FailedAuthenticationException extends RuntimeException {
    public FailedAuthenticationException(){
        super("Failed Authentication");
    }
}
