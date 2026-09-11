package com.kino.my_video_service.controller;

import com.kino.my_video_service.dto.subscription.SubscriptionCreateRequest;
import com.kino.my_video_service.dto.subscription.SubscriptionResponse;
import com.kino.my_video_service.entities.SubscriptionEntity;
import com.kino.my_video_service.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{id}/subscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponse createSubscription(
            @PathVariable Long id,
            @RequestBody @Valid SubscriptionCreateRequest createRequest
    ){
        SubscriptionEntity newSubscription = subscriptionService.createSubscription(id, createRequest.getPlan());
        return toResponse(newSubscription);
    }

    private SubscriptionResponse toResponse(SubscriptionEntity newSubscription) {
        return new SubscriptionResponse(
                newSubscription.getPlan(), newSubscription.getCost(),
                newSubscription.getDateBegin(), newSubscription.getDateEnd()
        );
    }
}
