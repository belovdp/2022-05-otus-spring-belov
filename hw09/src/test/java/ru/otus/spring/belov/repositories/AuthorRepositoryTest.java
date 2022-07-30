package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.belov.domain.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с авторами")
@DataJpaTest
class AuthorRepositoryTest {

    private static final long EXISTING_AUTHOR_ID = 2;
    private static final String EXISTING_AUTHOR_NAME = "Джордж Реймонд Ричард Мартин";
    private static final LocalDate EXISTING_AUTHOR_BIRTHDAY = LocalDate.parse("1948-09-20");

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Тестирует сохранение записи")
    @Test
    void saveTest() {
        Author expectedAuthor = Author.builder().name("test").birthday(LocalDate.now()).build();
        authorRepository.save(expectedAuthor);
        Optional<Author> actualAuthor = authorRepository.findById(expectedAuthor.getId());
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
        List<Author> actualAuthorList = authorRepository.findAll();
        assertThat(actualAuthorList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("books")
                .contains(expectedAuthor);
        assertEquals(4, actualAuthorList.size(), "Неверное количество записей");
    }

    @DisplayName("Тестирует поиск записи по имени автора")
    @Test
    void findByNameContainingTest() {
        var authors = authorRepository.findByNameContainingIgnoreCase("орд");
        assertEquals(1, authors.size());
        assertEquals(2, authors.get(0).getId());
        authors = authorRepository.findByNameContainingIgnoreCase("ев");
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
        Optional<Author> actualAuthor = authorRepository.findById(expectedAuthor.getId());
        assertTrue(actualAuthor.isPresent(), "Не найден автор");
        assertTrue(new ReflectionEquals(expectedAuthor, "books").matches(actualAuthor.get()));
        assertEquals(4, actualAuthor.get().getBooks().size(), "Неверное количество книг");
    }
}