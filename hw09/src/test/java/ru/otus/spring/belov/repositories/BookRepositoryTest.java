package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
class BookRepositoryTest {

    private static final long EXISTING_BOOK_ID = 3L;
    @Autowired
    private BookRepository bookRepository;
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
        bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
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
        bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует удаление книги")
    @Test
    void deleteByIdTest() {
        var existingBookId = getExistingBook().getId();
        assertThat(bookRepository.findById(existingBookId)).isNotEmpty();
        bookRepository.deleteById(existingBookId);
        em.flush();
        assertThat(bookRepository.findById(existingBookId)).isEmpty();
    }

    @DisplayName("Тестирует поиск книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует поиск всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
        assertEquals(6, actualBookList.size(), "Неверное количество записей");
    }

    private Book getExistingBook() {
        var book = bookRepository.findById(EXISTING_BOOK_ID).orElseThrow(() -> new IllegalStateException("Ожидаемая книга не существет"));
        em.detach(book);
        return book;
    }
}