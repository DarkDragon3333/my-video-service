package com.kino.my_video_service.repository;

import com.kino.my_video_service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLogin(String login);
}
