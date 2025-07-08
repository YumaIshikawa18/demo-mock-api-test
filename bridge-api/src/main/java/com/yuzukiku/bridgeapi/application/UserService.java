package com.yuzukiku.bridgeapi.application;

import com.yuzukiku.bridgeapi.infrastruture.MockServerClient;
import com.yuzukiku.bridgeapi.presentation.dto.UserRequest;
import com.yuzukiku.bridgeapi.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MockServerClient client;

    public UserResponse getUserData(UserRequest request) {
        var response = client.fetchUserData(request);
        var result = new UserResponse();
        result.setName(response.getName());
        result.setAge(response.getAge());
        result.setHobbies(response.getHobbies());
        return result;
    }
}
