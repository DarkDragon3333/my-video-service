package com.kino.my_video_service.service;

import com.kino.my_video_service.dto.cost_plan.PlanUpsertResult;
import com.kino.my_video_service.entities.CostPlanEntity;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.repository.CostPlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CostPlanService {
    private final CostPlanRepository costPlanRepository;


    public CostPlanService(CostPlanRepository costPlanRepository) {
        this.costPlanRepository = costPlanRepository;
    }

    public PlanUpsertResult upsertCostPlan(SubscriptionPlan plan, BigDecimal cost){
        boolean exist = costPlanRepository.existsById(plan);
        CostPlanEntity newCostPlan = new CostPlanEntity();
        newCostPlan.setPlan(plan);
        newCostPlan.setCost(cost);

        CostPlanEntity savedPlan = costPlanRepository.save(newCostPlan);
        return new PlanUpsertResult(savedPlan, exist);
    }
}
