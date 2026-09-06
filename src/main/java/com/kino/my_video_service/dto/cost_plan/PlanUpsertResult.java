package com.kino.my_video_service.dto.cost_plan;

import com.kino.my_video_service.entities.CostPlanEntity;

public record PlanUpsertResult(CostPlanEntity costPlan, boolean existed) {}
