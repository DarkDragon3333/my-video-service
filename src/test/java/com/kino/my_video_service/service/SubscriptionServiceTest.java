package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.CostPlanEntity;
import com.kino.my_video_service.entities.SubscriptionEntity;
import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.exception.subscription.CostPlanNotFoundException;
import com.kino.my_video_service.exception.subscription.SubscriptionAlreadyExistException;
import com.kino.my_video_service.exception.user.UserNotFoundException;
import com.kino.my_video_service.repository.CostPlanRepository;
import com.kino.my_video_service.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubscriptionServiceTest {
    private SubscriptionRepository subscriptionRepository;
    private SubscriptionService subscriptionService;
    private UserService userService;
    private CostPlanRepository costPlanRepository;

    @BeforeEach
    public void initObj() {
        subscriptionRepository = mock(SubscriptionRepository.class);
        costPlanRepository = mock(CostPlanRepository.class);
        userService = mock(UserService.class);

        subscriptionService = new SubscriptionService(subscriptionRepository, costPlanRepository, userService);
    }

    @Test
    public void createSubscription_NotFoundUserException() {
        Long id = 1L;
        SubscriptionPlan testPlan = SubscriptionPlan.BASE;

        when(userService.findUserById(id)).thenThrow(new UserNotFoundException(id));
        assertThrows(
                UserNotFoundException.class,
                () -> subscriptionService.createSubscription(id, testPlan)
        );

        verify(costPlanRepository, never()).findById(any());
        verify(subscriptionRepository, never()).existsByUserIdAndDateEndAfter(any(), any());
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    public void createSubscription_CostPlanNotFoundException() {
        Long id = 1L;
        UserEntity testUser = new UserEntity();
        SubscriptionPlan testPlan = SubscriptionPlan.BASE;

        when(userService.findUserById(id)).thenReturn(testUser);

        assertThrows(
                CostPlanNotFoundException.class,
                () -> subscriptionService.createSubscription(id, testPlan)
        );

        verify(subscriptionRepository, never()).existsByUserIdAndDateEndAfter(any(), any());
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    public void createSubscription_SubscriptionAlreadyExistException() {
        Long id = 1L;
        UserEntity testUser = new UserEntity();

        SubscriptionPlan testPlan = SubscriptionPlan.BASE;
        CostPlanEntity testCostPlan = new CostPlanEntity();
        BigDecimal testCost = BigDecimal.valueOf(100);

        Duration testDuration = Duration.ofDays(30);

        testCostPlan.setCost(testCost);
        testCostPlan.setPlan(testPlan);
        testCostPlan.setDuration(testDuration);

        when(userService.findUserById(id)).thenReturn(testUser);
        when(costPlanRepository.findById(testPlan)).thenReturn(Optional.of(testCostPlan));
        when(subscriptionRepository.existsByUserIdAndDateEndAfter(any(), any())).thenReturn(true);

        assertThrows(
                SubscriptionAlreadyExistException.class,
                () -> subscriptionService.createSubscription(id, testPlan)
        );

        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    public void createSubscription_SuccessOperation() {
        Long id = 1L;
        UserEntity testUser = new UserEntity();

        SubscriptionPlan testPlan = SubscriptionPlan.BASE;
        CostPlanEntity testCostPlan = new CostPlanEntity();
        BigDecimal testCost = BigDecimal.valueOf(100);

        Duration testDuration = Duration.ofDays(30);

        testCostPlan.setCost(testCost);
        testCostPlan.setPlan(testPlan);
        testCostPlan.setDuration(testDuration);

        when(userService.findUserById(id)).thenReturn(testUser);
        when(costPlanRepository.findById(testPlan)).thenReturn(Optional.of(testCostPlan));
        when(subscriptionRepository.existsByUserIdAndDateEndAfter(any(), any())).thenReturn(false);

        subscriptionService.createSubscription(id, testPlan);

        ArgumentCaptor<SubscriptionEntity> captor = ArgumentCaptor.forClass(SubscriptionEntity.class);
        verify(subscriptionRepository, times(1)).save(captor.capture());
        SubscriptionEntity captorSubscription = captor.getValue();

        assertEquals(testUser, captorSubscription.getUser());
        assertEquals(testPlan, captorSubscription.getPlan());
        assertEquals(testCost, captorSubscription.getCost());
        assertEquals(captorSubscription.getDateBegin(), captorSubscription.getDateEnd().minus(testDuration));
    }
}
