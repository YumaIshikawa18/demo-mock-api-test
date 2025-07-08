package com.yuzukiku.bridgeapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mock-server")
public class MockServerProperties {
    private String url;
}
