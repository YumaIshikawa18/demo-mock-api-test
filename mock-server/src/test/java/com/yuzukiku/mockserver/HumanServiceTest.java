package com.yuzukiku.mockserver;

import com.yuzukiku.mockserver.application.HumanService;
import com.yuzukiku.mockserver.domain.entity.Human;
import com.yuzukiku.mockserver.domain.repository.HumanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HumanServiceTest {

    @Mock
    private HumanRepository humanRepository;

    @InjectMocks
    private HumanService humanService;

    @ParameterizedTest(name = "[{index}] name={0}, expectedSize={1}")
    @DisplayName("名前を指定したとき、対応する件数のリストを返す")
    @CsvSource(delimiterString = "|", textBlock = """
            Alice|1
            Bob|2
            Charlie|0
            """)
    void givenName_whenGetByName_thenReturnListOfSize(String name, int expectedSize) {
        // given
        List<Human> mockList;
        switch (name) {
            case "Alice" -> mockList = List.of(
                    new Human(UUID.randomUUID(), "Alice", 25, "Osaka", List.of("reading"), "alice@example.com")
            );
            case "Bob" -> mockList = List.of(
                    new Human(UUID.randomUUID(), "Bob", 30, "Tokyo", List.of("music"), "bob@example.com"),
                    new Human(UUID.randomUUID(), "Bob", 35, "Kyoto", List.of("travel"), "bob2@example.com")
            );
            default -> mockList = Collections.emptyList();
        }
        when(humanRepository.findByName(name)).thenReturn(mockList);

        // when
        List<Human> result = humanService.getByName(name);

        // then
        assertThat(result).hasSize(expectedSize);
        verify(humanRepository, times(1)).findByName(name);
    }

    @ParameterizedTest(name = "[{index}] name={0}, age={1}, address={2}")
    @DisplayName("Human情報を指定したとき、同じデータで保存されたHumanを返す")
    @CsvSource(delimiterString = "|", textBlock = """
            Dave|40|Nagoya
            Eve|22|Sapporo
            """)
    void givenHumanInfo_whenCreate_thenReturnSavedHuman(String name, int age, String address) {
        // given
        Human input = new Human(null, name, age, address, List.of("hobby"), name.toLowerCase() + "@example.com");
        Human saved = new Human(UUID.randomUUID(), name, age, address, List.of("hobby"), name.toLowerCase() + "@example.com");
        when(humanRepository.save(input)).thenReturn(saved);

        // when
        Human result = humanService.create(input);

        // then
        assertThat(result.getId()).isEqualTo(saved.getId());
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getAge()).isEqualTo(age);
        assertThat(result.getAddress()).isEqualTo(address);
        verify(humanRepository, times(1)).save(input);
    }
}
