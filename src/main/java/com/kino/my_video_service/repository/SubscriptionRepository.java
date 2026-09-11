package com.kino.my_video_service.repository;

import com.kino.my_video_service.entities.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    boolean existsByUserIdAndDateEndAfter(Long id, Instant currentDate);
}
