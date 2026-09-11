package com.kino.my_video_service.dto.subscription;

import com.kino.my_video_service.enums.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class SubscriptionResponse {
    private SubscriptionPlan plan;
    private BigDecimal cost;
    private Instant dateBegin;
    private Instant dateEnd;
}
