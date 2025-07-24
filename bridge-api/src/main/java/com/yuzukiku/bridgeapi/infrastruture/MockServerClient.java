package com.yuzukiku.bridgeapi.infrastruture;

import com.yuzukiku.bridgeapi.presentation.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockServerClient {
    private final RestClient.Builder restClientBuilder;

    public List<MockUserResponse> fetchUserData(UserRequest request) {
        return restClientBuilder
                .build()
                .post()
                .uri("/humans/name")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
