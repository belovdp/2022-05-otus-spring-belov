package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.belov.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с авторами")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final long EXISTING_GENRE_ID = 2;
    private static final String EXISTING_GENRE_NAME = "Фэнтези";

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @DisplayName("Тестирует сохранение записи")
    @Test
    void saveTest() {
        Genre expectedGenre = Genre.builder().name("test").build();
        genreRepositoryJpa.save(expectedGenre);
        Optional<Genre> actualGenre = genreRepositoryJpa.findById(expectedGenre.getId());
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
        List<Genre> actualGenreList = genreRepositoryJpa.findAll();
        assertThat(actualGenreList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("books")
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
        Optional<Genre> actualGenre = genreRepositoryJpa.findByName(EXISTING_GENRE_NAME);
        assertTrue(actualGenre.isPresent(), "Не найден автор");
        assertTrue(new ReflectionEquals(expectedGenre, "books").matches(actualGenre.get()), "Неверная запись");
        assertEquals(3, actualGenre.get().getBooks().size(), "Неверное количество книг");
    }

    @DisplayName("Тестирует поиск записи по id")
    @Test
    void findByIdTest() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTING_GENRE_ID)
                .name(EXISTING_GENRE_NAME)
                .build();
        Optional<Genre> actualGenre = genreRepositoryJpa.findById(expectedGenre.getId());
        assertTrue(actualGenre.isPresent(), "Не найден автор");
        assertTrue(new ReflectionEquals(expectedGenre, "books").matches(actualGenre.get()), "Неверная запись");
        assertEquals(3, actualGenre.get().getBooks().size(), "Неверное количество книг");
    }
}