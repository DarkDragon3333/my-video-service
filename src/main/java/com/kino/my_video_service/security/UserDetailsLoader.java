package com.kino.my_video_service.security;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByLoginAndIsDeletedFalse(username);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            return new SecurityUser(userEntity.getLogin(), userEntity.getPasswordHash(), userEntity.getId());
        } else {
            throw new UsernameNotFoundException("Username not found!");
        }
    }
}
