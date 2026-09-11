package com.kino.my_video_service.exception.subscription;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubscriptionAlreadyExistException extends RuntimeException {
    public SubscriptionAlreadyExistException(Long id) {
        super("Subscription already taken! User id: " + id);
    }
}
