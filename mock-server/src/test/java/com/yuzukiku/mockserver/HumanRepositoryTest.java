package com.yuzukiku.mockserver;

import com.yuzukiku.mockserver.domain.entity.Human;
import com.yuzukiku.mockserver.domain.repository.HumanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@Import(TestcontainersConfiguration.class)
public class HumanRepositoryTest {

    @Autowired
    private HumanRepository repository;

    @Test
    @DisplayName("保存済みのHumanがあるとき findAllで全件取得できる")
    void givenHumansSaved_whenFindAll_thenReturnAllHumans() {
        // given
        Human h1 = new Human(null, "一郎", 20, "大阪", List.of("読書"), "ichiro@example.com");
        Human h2 = new Human(null, "二郎", 25, "東京", List.of("音楽"), "jiro@example.com");
        repository.save(h1);
        repository.save(h2);

        // when
        List<Human> result = repository.findAll();

        // then
        assertThat(result)
                .hasSize(2)
                .extracting(Human::getName)
                .containsExactlyInAnyOrder("一郎", "二郎");
    }

    @Test
    @DisplayName("保存済みのHumanにマッチする名前があるとき findByNameで該当リストを取得できる")
    void givenHumansSaved_whenFindByName_thenReturnMatchingHumans() {
        // given
        Human h = new Human(null, "三郎", 30, "京都", List.of("剣道"), "saburo@example.com");
        repository.save(h);

        // when
        List<Human> result = repository.findByName("三郎");

        // then
        assertThat(result)
                .hasSize(1)
                .allSatisfy(e -> assertThat(e.getName()).isEqualTo("三郎"));
    }

    @Test
    @DisplayName("新規Humanを保存するとき saveでIDが生成されプロパティが保持される")
    void givenNewHuman_whenSave_thenReturnSavedEntity() {
        // given
        Human newHuman = new Human(null, "四郎", 40, "名古屋", List.of("囲碁"), "shiro@example.com");

        // when
        Human saved = repository.save(newHuman);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("四郎");
        assertThat(saved.getAge()).isEqualTo(40);
        assertThat(saved.getAddress()).isEqualTo("名古屋");
        assertThat(saved.getHobbies()).containsExactly("囲碁");
        assertThat(saved.getEmail()).isEqualTo("shiro@example.com");
    }
}
