package ru.otus.spring.belov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.belov.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест DAO для работы с авторами")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final long EXISTING_GENRE_ID = 2;
    private static final String EXISTING_GENRE_NAME = "Фэнтези";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @DisplayName("Тестирует сохранение записи")
    @Test
    void saveTest() {
        Genre expectedGenre = Genre.builder().name("test").build();
        genreDaoJdbc.save(expectedGenre);
        Optional<Genre> actualGenre = genreDaoJdbc.findById(expectedGenre.getId());
        assertTrue(actualGenre.isPresent(), "Не найден автор");
        assertThat(actualGenre.get()).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Тестирует поиск списка записей")
    @Test
    void findAllTest() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTING_GENRE_ID)
                .name(EXISTING_GENRE_NAME)
                .build();
        List<Genre> actualGenreList = genreDaoJdbc.findAll();
        assertThat(actualGenreList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedGenre);
        assertEquals(5, actualGenreList.size(), "Неверное количество записей");
    }

    @DisplayName("Тестирует поиск записи по имени автора")
    @Test
    void findByNameTest() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTING_GENRE_ID)
                .name(EXISTING_GENRE_NAME)
                .build();
        Optional<Genre> actualGenre = genreDaoJdbc.findByName(EXISTING_GENRE_NAME);
        assertTrue(actualGenre.isPresent(), "Не найден автор");
        assertThat(actualGenre.get()).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Тестирует поиск записи по id")
    @Test
    void findByIdTest() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTING_GENRE_ID)
                .name(EXISTING_GENRE_NAME)
                .build();
        Optional<Genre> actualGenre = genreDaoJdbc.findById(expectedGenre.getId());
        assertTrue(actualGenre.isPresent(), "Не найден автор");
        assertThat(actualGenre.get()).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}