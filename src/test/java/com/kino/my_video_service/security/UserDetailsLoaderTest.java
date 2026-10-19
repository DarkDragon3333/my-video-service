package com.kino.my_video_service.security;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsLoaderTest {
    private UserRepository userRepository;
    private UserDetailsLoader userDetailsLoader;

    @BeforeEach
    public void initObj() {
        userRepository = mock(UserRepository.class);
        userDetailsLoader = new UserDetailsLoader(userRepository);
    }

    @Test
    public void loadUserByUsername_UsernameNotFoundException() {
        String testUsername = "username";
        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsLoader.loadUserByUsername(testUsername)
        );
    }

    @Test
    public void loadUserByUsername_SuccessLoad() {
        String testUsername = "username";
        String testPasswordHash = "password";
        Long testId = -5L;
        UserEntity userEntity = new UserEntity();

        userEntity.setLogin(testUsername);
        userEntity.setPasswordHash(testPasswordHash);
        userEntity.setId(testId);

        when(userRepository.findByLoginAndIsDeletedFalse(testUsername)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsLoader.loadUserByUsername(testUsername);

        SecurityUser securityUser = assertInstanceOf(SecurityUser.class, userDetails);
        assertEquals(testUsername, securityUser.getUsername());
        assertEquals(testPasswordHash, securityUser.getPassword());
        assertEquals(testId, securityUser.getId());
    }
}
