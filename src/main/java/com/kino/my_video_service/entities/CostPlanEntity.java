package com.kino.my_video_service.entities;

import com.kino.my_video_service.enums.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cost_plans")
public class CostPlanEntity {
    @Id
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private Duration duration;
}
