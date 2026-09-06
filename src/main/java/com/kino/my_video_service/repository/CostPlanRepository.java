package com.kino.my_video_service.repository;

import com.kino.my_video_service.entities.CostPlanEntity;
import com.kino.my_video_service.enums.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostPlanRepository extends JpaRepository<CostPlanEntity, SubscriptionPlan> {

}
