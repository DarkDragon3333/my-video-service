package com.kino.my_video_service.exceprion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LoginAlreadyTakenException extends RuntimeException{
    public LoginAlreadyTakenException(String msg){
        super(msg);
    }
}
