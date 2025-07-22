package com.yuzukiku.bridgeapi;

import com.yuzukiku.bridgeapi.config.RestClientConfig;
import com.yuzukiku.bridgeapi.infrastruture.MockServerClient;
import com.yuzukiku.bridgeapi.infrastruture.MockUserResponse;
import com.yuzukiku.bridgeapi.presentation.dto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MockServerClient.class)
@Import(RestClientConfig.class)
@TestPropertySource(properties = "mock-server.url=http://localhost:8081")
public class MockServerClientTest {

    @Autowired
    private MockServerClient client;

    @Autowired
    private RestClient.Builder restClientBuilder;

    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        server = MockRestServiceServer.bindTo(restClientBuilder).build();
    }

    @Test
    @DisplayName("田中という名前でユーザーデータを取得すると、モックレスポンスが返されること")
    void givenName_whenFetchUser_thenReturnMockUserResponse() {
        var request = new UserRequest();
        request.setName("田中");

        String json = """
        {
          "id": "123e4567-e89b-12d3-a456-426614174000",
          "name": "田中",
          "age": 25,
          "hobbies": [
            "読書"
          ]
        }
        """;
        server.expect(requestTo("http://localhost:8081/humans/name"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        MockUserResponse response = client.fetchUserData(request);
        assertThat(response.getName()).isEqualTo("田中");
        assertThat(response.getAge()).isEqualTo(25);
        assertThat(response.getHobbies()).containsExactly("読書");

        server.verify();
    }
}
