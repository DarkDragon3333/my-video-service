package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.CostPlanEntity;
import com.kino.my_video_service.entities.SubscriptionEntity;
import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.exception.subscription.SubscriptionAlreadyExistException;
import com.kino.my_video_service.exception.subscription.CostPlanNotFoundException;
import com.kino.my_video_service.repository.CostPlanRepository;
import com.kino.my_video_service.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final CostPlanRepository costPlanRepository;
    private final UserService userService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, CostPlanRepository costPlanRepository, UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.costPlanRepository = costPlanRepository;
        this.userService = userService;
    }

    public SubscriptionEntity createSubscription(Long id, SubscriptionPlan plan) {
        UserEntity userEntity = userService.findUserById(id);
        Instant currentDate = Instant.now();

        CostPlanEntity costPlan =
                costPlanRepository
                        .findById(plan)
                        .orElseThrow(() -> new CostPlanNotFoundException(plan));

        if (subscriptionRepository.existsByUserIdAndDateEndAfter(id, currentDate)) {
            throw new SubscriptionAlreadyExistException(id);
        }


        SubscriptionEntity newSubscription = new SubscriptionEntity();
        newSubscription.setUser(userEntity);
        newSubscription.setPlan(plan);
        newSubscription.setCost(costPlan.getCost());
        newSubscription.setDateBegin(currentDate);
        newSubscription.setDateEnd(currentDate.plus(costPlan.getDuration()));

        return subscriptionRepository.save(newSubscription);

    }
}