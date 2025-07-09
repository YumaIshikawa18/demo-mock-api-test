package com.yuzukiku.mockserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzukiku.mockserver.application.HumanService;
import com.yuzukiku.mockserver.domain.entity.Human;
import com.yuzukiku.mockserver.exception.GlobalExceptionHandler;
import com.yuzukiku.mockserver.presentation.HumanController;
import com.yuzukiku.mockserver.presentation.NameRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HumanControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private HumanService humanService;

    @InjectMocks
    private HumanController humanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(humanController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("全件取得 → POST /humans で全Humanリストを返す")
    void givenHumansExist_whenPostToHumans_thenReturnList() throws Exception {
        List<Human> humans = List.of(
                new Human(UUID.randomUUID(), "Taro", 30, "Tokyo", List.of("music"), "taro@example.com"),
                new Human(UUID.randomUUID(), "Jiro", 25, "Osaka", List.of("sports"), "jiro@example.com")
        );
        when(humanService.getAll()).thenReturn(humans);

        mockMvc.perform(post("/humans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(humans)));
    }

    @Test
    @DisplayName("全件取得 → Service例外時に500を返す")
    void givenServiceThrows_whenPostToHumans_thenInternalError() throws Exception {
        when(humanService.getAll()).thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(post("/humans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("DB error"))
                .andExpect(jsonPath("$.details").value("Internal server error (500)"));
    }

    @ParameterizedTest
    @CsvSource(delimiterString = "|", textBlock = """
            Alice|1
            Bob|2
            """)
    @DisplayName("名前検索 → POST /humans で指定名のリストを返す")
    void givenName_whenPostToHumansWithBody_thenReturnFilteredList(String name, int size) throws Exception {
        List<Human> list = switch (name) {
            case "Alice" -> List.of(new Human(UUID.randomUUID(), "Alice", 20, "X", List.of(), "alice@ex"));
            case "Bob"   -> List.of(
                    new Human(UUID.randomUUID(), "Bob", 22, "Y", List.of(), "bob@ex"),
                    new Human(UUID.randomUUID(), "Bob", 24, "Z", List.of(), "bob2@ex")
            );
            default      -> List.of();
        };
        when(humanService.getByName(name)).thenReturn(list);

        String req = objectMapper.writeValueAsString(new NameRequest(name));

        mockMvc.perform(post("/humans/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(req))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(size)));
    }

    @Test
    @DisplayName("作成 → POST /humans/create でHumanを保存して返す")
    void givenHuman_whenPostToHumansCreate_thenReturnSaved() throws Exception {
        Human input = new Human(null, "Hanako", 28, "Kyoto", List.of("reading"), "hanako@example.com");
        Human saved = new Human(UUID.randomUUID(), "Hanako", 28, "Kyoto", List.of("reading"), "hanako@example.com");
        when(humanService.create(any(Human.class))).thenReturn(saved);

        String reqJson = objectMapper.writeValueAsString(input);

        mockMvc.perform(post("/humans/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(reqJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().toString()))
                .andExpect(jsonPath("$.name").value("Hanako"));
    }

    @Test
    @DisplayName("作成 → Service例外時に500を返す")
    void givenServiceThrows_whenCreate_thenInternalError() throws Exception {
        when(humanService.create(any(Human.class))).thenThrow(new RuntimeException("Create error"));

        String reqJson = objectMapper.writeValueAsString(
                new Human(null, "Hanako", 28, "Kyoto", List.of("reading"), "hanako@example.com")
        );

        mockMvc.perform(post("/humans/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(reqJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Create error"))
                .andExpect(jsonPath("$.details").value("Internal server error (500)"));
    }
}
