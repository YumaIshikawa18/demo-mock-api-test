package com.yuzukiku.bridgeapi.infrastruture;

import com.yuzukiku.bridgeapi.config.MockServerProperties;
import com.yuzukiku.bridgeapi.presentation.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class MockServerClient {
    private final RestClient.Builder restClientBuilder;
    private final MockServerProperties properties;

    public MockUserResponse fetchUserData(UserRequest request) {
        return restClientBuilder
                .build()
                .post()
                .uri(properties.getUrl() + "/humans")
                .body(request)
                .retrieve()
                .toEntity(MockUserResponse.class)
                .getBody();
    }
}
