package com.kino.my_video_service.service;

import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.exception.*;
import com.kino.my_video_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void initObj() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void createUser_LoginAlreadyTakenException() {
        when(userRepository.existsByLogin("max")).thenReturn(true);
        assertThrows(
                LoginAlreadyTakenException.class,
                () -> userService.createUser("max", "max", "max")
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    public void createUser_successCreate() {
        String login = "max@mail.com";
        String displayName = "Max";
        String password = "123456789";
        String passwordHash = "hashed-password";

        when(passwordEncoder.encode(password)).thenReturn(passwordHash);
        userService.createUser(login, displayName, password);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, times(1)).save(captor.capture());

        UserEntity capturedUser = captor.getValue();
        assertEquals(login, capturedUser.getLogin());
        assertEquals(displayName, capturedUser.getDisplayName());
        assertEquals(passwordHash, capturedUser.getPasswordHash());
    }

    @Test
    public void authenticationUser_LoginFailedAuthenticationException() {
        String testLogin = "testLogin";
        String testPassword = "testPassword";
        assertThrows(
                FailedAuthenticationException.class,
                () -> userService.authenticationUser(testLogin, testPassword)
        );
    }

    @Test
    public void authenticationUser_PasswordFailedAuthenticationException() {
        UserEntity testUser = new UserEntity();
        String testLogin = "testLogin";
        String testPassword = "testPassword";
        when(userRepository.findByLogin(testLogin)).thenReturn(Optional.of(testUser));
        assertThrows(
                FailedAuthenticationException.class,
                () -> userService.authenticationUser(testLogin, testPassword)
        );
    }

    @Test
    public void authenticationUser_successAuthentication() {
        UserEntity testUser = new UserEntity();
        String testLogin = "testLogin";
        String testPassword = "testPassword";
        when(userRepository.findByLogin(testLogin)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(testPassword, testUser.getPasswordHash())).thenReturn(true);
        UserEntity testLoadUser = userService.authenticationUser(testLogin, testPassword);
        assertEquals(testUser, testLoadUser);
    }

    @Test
    public void findUserById_whenUserMissing_throwsUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(0L));
    }

    @Test
    public void findUserById_successSearch() {
        UserEntity testUser = new UserEntity();
        when(userRepository.findById(10L)).thenReturn(Optional.of(testUser));
        assertEquals(
                testUser,
                userService.findUserById(10L)
        );
    }

    @Test
    public void patchLogin_SameLoginException() {
        UserEntity testUser = new UserEntity();
        String testLogin = "testLogin";
        testUser.setLogin(testLogin);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        assertThrows(
                SameLoginException.class,
                () -> userService.patchLogin(1L, testLogin)
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    public void patchLogin_LoginAlreadyTakenException() {
        UserEntity testUser = new UserEntity();
        String userLogin = "userLogin";
        String testNewLogin = "newLogin";
        testUser.setLogin(userLogin);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByLogin(testNewLogin)).thenReturn(true);
        assertThrows(
                LoginAlreadyTakenException.class,
                () -> userService.patchLogin(1L, testNewLogin)
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    public void patchLogin_successPatch() {
        UserEntity testUser = new UserEntity();
        String userLogin = "userLogin";
        String testNewLogin = "newLogin";
        testUser.setLogin(userLogin);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        UserEntity testPatchUser = userService.patchLogin(1L, testNewLogin);
        verify(userRepository, times(1)).save(any());

        assertEquals(
                testNewLogin,
                testUser.getLogin()
        );
        assertEquals(
                testUser,
                testPatchUser
        );
    }

    @Test
    public void patchPassword_WrongPasswordException() {
        UserEntity testUser = new UserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        assertThrows(
                WrongPasswordException.class,
                () -> userService.patchPassword(1L, "oldPass", "newPass")
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    public void patchPassword_successPatch() {
        UserEntity testUser = new UserEntity();
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String hashed = "hashed-password";

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(oldPassword, testUser.getPasswordHash())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(hashed);

        userService.patchPassword(1L, oldPassword, newPassword);

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, times(1)).save(captor.capture());

        UserEntity captorUser = captor.getValue();
        assertEquals(hashed, captorUser.getPasswordHash());
    }
}
