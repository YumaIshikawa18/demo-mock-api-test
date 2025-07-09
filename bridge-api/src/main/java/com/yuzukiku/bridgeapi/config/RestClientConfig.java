package com.yuzukiku.bridgeapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

@Configuration
@EnableConfigurationProperties(MockServerProperties.class)
@RequiredArgsConstructor
public class RestClientConfig {
    private final MockServerProperties properties;

    @Bean
    public Builder restClient() {
        return RestClient
                .builder()
                .baseUrl(properties.getUrl());
    }
}
