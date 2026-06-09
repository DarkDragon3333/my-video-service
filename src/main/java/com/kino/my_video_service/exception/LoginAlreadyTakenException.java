package com.kino.my_video_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LoginAlreadyTakenException extends RuntimeException{
    public LoginAlreadyTakenException(String msg){
        super(msg);
    }
}
