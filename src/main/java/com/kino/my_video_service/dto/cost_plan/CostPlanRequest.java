package com.kino.my_video_service.dto.cost_plan;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CostPlanRequest {
    @NotNull
    @PositiveOrZero
    @Digits(integer = 5, fraction = 2)
    private BigDecimal cost;
}
