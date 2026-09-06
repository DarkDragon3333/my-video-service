package com.kino.my_video_service.dto.cost_plan;

import com.kino.my_video_service.enums.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CostPlanResponse {
    private SubscriptionPlan plan;
    private BigDecimal cost;
}
