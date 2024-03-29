package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с книгами")
@DataJpaTest
@Import({BookRepositoryJpa.class})
class BookRepositoryJpaTest {

    private static final long EXISTING_BOOK_ID = 3L;
    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Тестирует сохранение книги")
    @Test
    void saveTest() {
        Book expectedBook = Book.builder()
                .title("test")
                .published(LocalDate.now())
                .genre(em.find(Genre.class, 2L))
                .author(em.find(Author.class, 2L))
                .build();
        bookRepositoryJpa.saveOrUpdate(expectedBook);
        Optional<Book> actualBook = bookRepositoryJpa.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует обновление книги")
    @Test
    void updateTest() {
        Book expectedBook = Book.builder()
                .id(2L)
                .title("test")
                .published(LocalDate.now())
                .genre(em.find(Genre.class, 2L))
                .author(em.find(Author.class, 2L))
                .build();
        bookRepositoryJpa.saveOrUpdate(expectedBook);
        Optional<Book> actualBook = bookRepositoryJpa.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует удаление книги")
    @Test
    void deleteByIdTest() {
        var existingBookId = getExistingBook().getId();
        assertThat(bookRepositoryJpa.findById(existingBookId)).isNotEmpty();
        bookRepositoryJpa.deleteById(existingBookId);
        em.flush();
        assertThat(bookRepositoryJpa.findById(existingBookId)).isEmpty();
    }

    @DisplayName("Тестирует поиск книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookRepositoryJpa.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertTrue(new ReflectionEquals(expectedBook, "comments").matches(actualBook.get()));
    }

    @DisplayName("Тестирует поиск всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        List<Book> actualBookList = bookRepositoryJpa.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("comments")
                .contains(expectedBook);
        assertEquals(6, actualBookList.size(), "Неверное количество записей");
    }

    private Book getExistingBook() {
        var book = bookRepositoryJpa.findById(EXISTING_BOOK_ID).orElseThrow(() -> new IllegalStateException("Ожидаемая книга не существет"));
        em.detach(book);
        return book;
    }
}