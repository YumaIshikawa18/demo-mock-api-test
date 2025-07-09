package com.yuzukiku.bridgeapi.presentation;

import com.yuzukiku.bridgeapi.application.UserService;
import com.yuzukiku.bridgeapi.presentation.dto.UserRequest;
import com.yuzukiku.bridgeapi.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bridge")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponse getUser(@RequestBody UserRequest request) {
        return userService.getUserData(request);
    }
}
