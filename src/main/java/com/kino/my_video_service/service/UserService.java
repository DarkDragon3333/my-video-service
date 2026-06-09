package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.exception.LoginAlreadyTakenException;
import com.kino.my_video_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public UserEntity createUser(String login, String displayName, String password) {
        if (userRepository.existsByLogin(login)) {
            throw new LoginAlreadyTakenException("Login already taken");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);
        userEntity.setDisplayName(displayName);
        userEntity.setPasswordHash(password); // Надо захешировать пароль

        return userRepository.save(userEntity);
    }
}
