package com.kino.my_video_service.service;


import com.kino.my_video_service.dto.cost_plan.PlanUpsertResult;
import com.kino.my_video_service.entities.CostPlanEntity;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.repository.CostPlanRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class CostPlanServiceTest {
    private CostPlanRepository costPlanRepository;
    private CostPlanService costPlanService;

    @BeforeEach
    public void initObj() {
        this.costPlanRepository = mock(CostPlanRepository.class);
        this.costPlanService = new CostPlanService(costPlanRepository);
    }

    @Test
    public void upsertCostPlan_SuccessUpsert(){
        boolean testExist = true;
        SubscriptionPlan plan = SubscriptionPlan.BASE;
        BigDecimal cost = BigDecimal.valueOf(200);
        Integer durationDays = 30;

        CostPlanEntity testCostPlan = new CostPlanEntity();
        testCostPlan.setPlan(plan);
        testCostPlan.setCost(cost);

        when(costPlanRepository.existsById(plan)).thenReturn(testExist);
        when(costPlanRepository.save(any())).thenReturn(testCostPlan);

        PlanUpsertResult testResult = costPlanService.upsertCostPlan(plan, cost, durationDays);

        ArgumentCaptor<CostPlanEntity> captor = ArgumentCaptor.forClass(CostPlanEntity.class);
        verify(costPlanRepository, times(1)).save(captor.capture());

        CostPlanEntity captureEntity = captor.getValue();

        assertEquals(plan, captureEntity.getPlan());
        assertEquals(cost, captureEntity.getCost());
        assertEquals(testExist, testResult.existed());
        assertEquals(durationDays, Math.toIntExact(captureEntity.getDuration().toDays()));
    }

}
