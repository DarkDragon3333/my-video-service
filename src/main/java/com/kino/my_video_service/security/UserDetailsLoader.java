package com.kino.my_video_service.security;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.exception.user.UserNotFoundException;
import com.kino.my_video_service.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
            UserEntity user = optionalUser.get();
            User.UserBuilder builder = User.withUsername(user.getLogin());
            return builder
                    .password(user.getPasswordHash())
                    .authorities(List.of())
                    .build();
        } else {
            throw new UsernameNotFoundException("Username not found!");
        }
    }
}
