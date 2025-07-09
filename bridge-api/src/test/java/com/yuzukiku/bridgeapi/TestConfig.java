package com.yuzukiku.bridgeapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

@Configuration
public class TestConfig {

    @Bean
    public MockRestServiceServer mockRestServiceServer(RestClient.Builder builder) {
        return MockRestServiceServer.bindTo(builder).build();
    }

    @Bean
    @Primary
    @DependsOn("mockRestServiceServer")
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}
