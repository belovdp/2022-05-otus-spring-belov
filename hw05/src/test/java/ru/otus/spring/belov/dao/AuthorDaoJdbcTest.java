package ru.otus.spring.belov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.belov.domain.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест DAO для работы с авторами")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final long EXISTING_AUTHOR_ID = 2;
    private static final String EXISTING_AUTHOR_NAME = "Джордж Реймонд Ричард Мартин";
    private static final LocalDate EXISTING_AUTHOR_BIRTHDAY = LocalDate.parse("1948-09-20");

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @DisplayName("Тестирует сохранение записи")
    @Test
    void saveTest() {
        Author expectedAuthor = Author.builder().name("test").birthday(LocalDate.now()).build();
        authorDaoJdbc.save(expectedAuthor);
        Optional<Author> actualAuthor = authorDaoJdbc.findById(expectedAuthor.getId());
        assertTrue(actualAuthor.isPresent(), "Не найден автор");
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Тестирует поиск списка записей")
    @Test
    void findAllTest() {
        Author expectedAuthor = Author.builder()
                .id(EXISTING_AUTHOR_ID)
                .name(EXISTING_AUTHOR_NAME)
                .birthday(EXISTING_AUTHOR_BIRTHDAY)
                .build();
        List<Author> actualAuthorList = authorDaoJdbc.findAll();
        assertThat(actualAuthorList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedAuthor);
        assertEquals(4, actualAuthorList.size(), "Неверное количество записей");
    }

    @DisplayName("Тестирует поиск записи по имени автора")
    @Test
    void findByNameContainingTest() {
        var authors = authorDaoJdbc.findByNameContaining("орд");
        assertEquals(1, authors.size());
        assertEquals(2, authors.get(0).getId());
        authors = authorDaoJdbc.findByNameContaining("ев");
        assertEquals(2, authors.size());
        assertTrue(List.of(1L, 3L).containsAll(authors.stream().map(Author::getId).toList()));
    }

    @DisplayName("Тестирует поиск записи по id")
    @Test
    void findByIdTest() {
        Author expectedAuthor = Author.builder()
                .id(EXISTING_AUTHOR_ID)
                .name(EXISTING_AUTHOR_NAME)
                .birthday(EXISTING_AUTHOR_BIRTHDAY)
                .build();
        Optional<Author> actualAuthor = authorDaoJdbc.findById(expectedAuthor.getId());
        assertTrue(actualAuthor.isPresent(), "Не найден автор");
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}