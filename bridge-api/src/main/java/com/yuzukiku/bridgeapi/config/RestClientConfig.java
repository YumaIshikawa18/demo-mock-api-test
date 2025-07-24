package com.yuzukiku.bridgeapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

@Configuration
@EnableConfigurationProperties(MockServerProperties.class)
@RequiredArgsConstructor
public class RestClientConfig {

    /**
     * RestClient.Builder を Bean として登録。
     * テストで MockRestServiceServer.bindTo(builder) が使えるようにするため。
     */
    @Bean
    public RestClient.Builder restClientBuilder(MockServerProperties props) {
        var jdkClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var factory = new JdkClientHttpRequestFactory(jdkClient);

        return RestClient.builder()
                .baseUrl(props.getUrl())
                .requestFactory(factory);
    }

    /**
     * 実際のリクエスト実行用 RestClient は builder から生成。
     */
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}
