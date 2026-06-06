package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public UserEntity createUser(UserEntity userEntity) {
        if (userRepository.existsByLogin(userEntity.getLogin())) {
            throw new IllegalStateException("Login already taken");
        }
        return userRepository.save(userEntity);
    }
}
