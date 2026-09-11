package com.kino.my_video_service.dto.subscription;

import com.kino.my_video_service.enums.SubscriptionPlan;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionCreateRequest {
    @NotNull
    private SubscriptionPlan plan;
}
