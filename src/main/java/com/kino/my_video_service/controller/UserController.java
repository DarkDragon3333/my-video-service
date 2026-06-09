package com.kino.my_video_service.controller;

import com.kino.my_video_service.dto.CreateUserRequest;
import com.kino.my_video_service.dto.UserResponse;
import com.kino.my_video_service.entities.UserEntity;
import com.kino.my_video_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
