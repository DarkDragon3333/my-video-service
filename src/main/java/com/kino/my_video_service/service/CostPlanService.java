package com.kino.my_video_service.service;

import com.kino.my_video_service.dto.cost_plan.PlanUpsertResult;
import com.kino.my_video_service.entities.CostPlanEntity;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.repository.CostPlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
public class CostPlanService {
    private final CostPlanRepository costPlanRepository;


    public CostPlanService(CostPlanRepository costPlanRepository) {
        this.costPlanRepository = costPlanRepository;
    }

    public PlanUpsertResult upsertCostPlan(SubscriptionPlan plan, BigDecimal cost, Integer durationDays){
        boolean exist = costPlanRepository.existsById(plan);
        Duration duration = Duration.ofDays(durationDays);
        CostPlanEntity costPlan = new CostPlanEntity();
        costPlan.setPlan(plan);
        costPlan.setCost(cost);
        costPlan.setDuration(duration);

        CostPlanEntity savedPlan = costPlanRepository.save(costPlan);
        return new PlanUpsertResult(savedPlan, exist);
    }

    public List<CostPlanEntity> findAll() {
        return costPlanRepository.findAll();
    }

    public void deletePlan(SubscriptionPlan plan){
        costPlanRepository.deleteById(plan);
    }
}
