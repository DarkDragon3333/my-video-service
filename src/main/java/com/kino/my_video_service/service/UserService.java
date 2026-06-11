package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.exception.FailedAuthenticationException;
import com.kino.my_video_service.exception.LoginAlreadyTakenException;
import com.kino.my_video_service.exception.UserNotFoundException;
import com.kino.my_video_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(String login, String displayName, String password) {
        if (userRepository.existsByLogin(login)) {
            throw new LoginAlreadyTakenException("Login already taken");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(login);
        userEntity.setDisplayName(displayName);
        userEntity.setPasswordHash(passwordEncoder.encode(password));

        return userRepository.save(userEntity);
    }

    public UserEntity authenticationUser(String login, String password) {
        UserEntity userEntity =
                userRepository.findByLogin(login).orElseThrow(FailedAuthenticationException::new);

        if (!passwordEncoder.matches(password, userEntity.getPasswordHash())) {
            throw new FailedAuthenticationException();
        }

        return userEntity;
    }

    public UserEntity findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
