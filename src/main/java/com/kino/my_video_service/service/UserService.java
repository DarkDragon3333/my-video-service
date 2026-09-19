package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.exception.user.*;
import com.kino.my_video_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        setUserFields(userEntity, login, displayName, passwordEncoder.encode(password), false);

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

    public UserEntity findUserById(Long id) {
        return userRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<UserEntity> findAll() {
        return userRepository.findAllByIsDeletedFalse();
    }

    public UserEntity patchDisplayName(Long id, String displayName) {
        UserEntity userEntity = findUserById(id);
        userEntity.setDisplayName(displayName);
        userRepository.save(userEntity);
        return userEntity;
    }

    public UserEntity patchLogin(Long id, String newLogin) {
        UserEntity userEntity = findUserById(id);

        if (newLogin.equals(userEntity.getLogin())) {
            throw new SameLoginException(id, userEntity.getLogin(), newLogin);
        }
        if (userRepository.existsByLogin(newLogin)) {
            throw new LoginAlreadyTakenException("Login already taken");
        }

        userEntity.setLogin(newLogin);
        userRepository.save(userEntity);
        return userEntity;
    }

    public void patchPassword(Long id, String oldPassword, String newPassword) {
        UserEntity userEntity = findUserById(id);

        if (!passwordEncoder.matches(oldPassword, userEntity.getPasswordHash())) {
            throw new WrongPasswordException(id);
        }

        userEntity.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findByIdAndIsDeletedFalse(id);

        if (optionalUser.isPresent()) {
            String maskLogin = UUID.randomUUID() + "_" + id;
            String maskDisplayName = "-";
            String maskPasswordHash = "-";
            boolean isDeleted = true;

            UserEntity user = optionalUser.get();
            setUserFields(user, maskLogin, maskDisplayName, maskPasswordHash, isDeleted);

            userRepository.save(user);
        }
    }

    private void setUserFields(
            UserEntity userEntity, String login,
            String displayName, String passwordHash,
            boolean isDeleted
    ) {
        userEntity.setLogin(login);
        userEntity.setDisplayName(displayName);
        userEntity.setPasswordHash(passwordHash);
        userEntity.setDeleted(isDeleted);
    }
}
