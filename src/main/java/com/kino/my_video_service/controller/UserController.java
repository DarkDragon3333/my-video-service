package com.kino.my_video_service.controller;

import com.kino.my_video_service.dto.*;
import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody CreateUserRequest request) {

        UserEntity tempEntity = userService.createUser(request.getLogin(), request.getDisplayName(), request.getPassword());
        return new UserResponse(tempEntity.getId(), tempEntity.getLogin(), tempEntity.getDisplayName());
    }

    @PostMapping("/users/authentication")
    public UserResponse authentication(@RequestBody AuthenticationRequest request){
        UserEntity userEntity = userService.authenticationUser(request.getLogin(), request.getPassword());
        return new UserResponse(userEntity.getId(), userEntity.getLogin(), userEntity.getDisplayName());
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        UserEntity userEntity = userService.findUserById(id);
        return new UserResponse(userEntity.getId(), userEntity.getLogin(), userEntity.getDisplayName());
    }

    @GetMapping()
    public List<UserResponse> findAll(){
        List<UserEntity> list = userService.findAll();

        return list.stream().map(
                user ->
                    new UserResponse(user.getId(), user.getLogin(), user.getDisplayName())

        ).toList();
    }

    @PatchMapping("/{id}")
    public UserResponse patchDisplayName(@PathVariable Long id,@RequestBody ChangeDisplayNameRequest request){
        UserEntity userEntity = userService.patchDisplayName(id, request.getDisplayName());
        return new UserResponse(userEntity.getId(), userEntity.getLogin(), userEntity.getDisplayName());
    }

    @PatchMapping("/{id}/login")
    public UserResponse patchLogin(@PathVariable Long id, @RequestBody ChangeLoginRequest request){
        UserEntity userEntity = userService.patchLogin(id, request.getLogin());
        return new UserResponse(userEntity.getId(), userEntity.getLogin(), userEntity.getDisplayName());
    }

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchPassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request){
        userService.patchPassword(id, request.getOldPassword(), request.getNewPassword());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

}