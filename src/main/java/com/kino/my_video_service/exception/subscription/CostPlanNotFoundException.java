package com.kino.my_video_service.exception.subscription;

import com.kino.my_video_service.enums.SubscriptionPlan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CostPlanNotFoundException extends RuntimeException {
    public CostPlanNotFoundException(SubscriptionPlan plan) {
        super("Plan " + plan + " does not exist");
    }
}
