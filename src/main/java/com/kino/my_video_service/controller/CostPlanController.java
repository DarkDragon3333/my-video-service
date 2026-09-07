package com.kino.my_video_service.controller;

import com.kino.my_video_service.dto.cost_plan.CostPlanRequest;
import com.kino.my_video_service.dto.cost_plan.CostPlanResponse;
import com.kino.my_video_service.dto.cost_plan.PlanUpsertResult;
import com.kino.my_video_service.entities.CostPlanEntity;
import com.kino.my_video_service.enums.SubscriptionPlan;
import com.kino.my_video_service.service.CostPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost-plans")
public class CostPlanController {
    private final CostPlanService costPlanService;

    public CostPlanController(CostPlanService costPlanService) {
        this.costPlanService = costPlanService;
    }

    @PutMapping("/{plan}")
    public ResponseEntity<CostPlanResponse> upsertCostPlan(@PathVariable SubscriptionPlan plan, @RequestBody @Valid CostPlanRequest costPlanRequest){
        PlanUpsertResult result = costPlanService.upsertCostPlan(plan, costPlanRequest.getCost());
        return result.existed() ?
                toResponseEntity(HttpStatus.OK, result) :
                toResponseEntity(HttpStatus.CREATED, result);
    }

    private ResponseEntity<CostPlanResponse> toResponseEntity(HttpStatus httpStatus, PlanUpsertResult result){
        return ResponseEntity.status(httpStatus).body(
                toResponse(result.costPlan())
        );
    }

    @GetMapping
    public List<CostPlanResponse> findAll(){
        List<CostPlanEntity> costPlanList = costPlanService.findAll();
        return costPlanList.stream().map(
                this::toResponse
        ).toList();

    }

    @DeleteMapping("/{plan}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCostPlan(@PathVariable SubscriptionPlan plan){
        costPlanService.deletePlan(plan);
    }

    private CostPlanResponse toResponse(CostPlanEntity costPlanEntity) {
        return new CostPlanResponse(costPlanEntity.getPlan(), costPlanEntity.getCost());
    }
}
