package com.yuzukiku.bridgeapi.application;

import com.yuzukiku.bridgeapi.infrastructure.MockServerClient;
import com.yuzukiku.bridgeapi.infrastructure.MockUserResponse;
import com.yuzukiku.bridgeapi.presentation.dto.UserRequest;
import com.yuzukiku.bridgeapi.presentation.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MockServerClient client;


    public List<UserResponse> getUserData(UserRequest request) {
        List<MockUserResponse> mockList = client.fetchUserData(request);

        return mockList.stream()
                .map(mock -> {
                    UserResponse resp = new UserResponse();
                    resp.setName(mock.getName());
                    resp.setAge(mock.getAge());
                    resp.setHobbies(mock.getHobbies());
                    return resp;
                })
                .toList();
    }
}
