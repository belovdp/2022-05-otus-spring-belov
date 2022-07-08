package ru.otus.spring.belov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест DAO для работы с книгами")
@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("Тестирует сохранение книги")
    @Test
    void saveTest() {
        Book expectedBook = Book.builder()
                .title("test")
                .published(LocalDate.now())
                .genre(Genre.builder().id(2L).name("Фэнтези").build())
                .author(Author.builder().id(4L).name("Пупкин").birthday(LocalDate.parse("1933-02-25")).build())
                .build();
        bookDaoJdbc.save(expectedBook);
        Optional<Book> actualBook = bookDaoJdbc.findById(expectedBook.getId());
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
                .genre(Genre.builder().id(2L).name("Фэнтези").build())
                .author(Author.builder().id(4L).name("Пупкин").birthday(LocalDate.parse("1933-02-25")).build())
                .build();
        bookDaoJdbc.update(expectedBook);
        Optional<Book> actualBook = bookDaoJdbc.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует удаление книги")
    @Test
    void deleteByIdTest() {
        var existingBookId = getExistingBook().getId();
        assertThat(bookDaoJdbc.findById(existingBookId)).isNotEmpty();
        bookDaoJdbc.deleteById(existingBookId);
        assertThat(bookDaoJdbc.findById(existingBookId)).isEmpty();
    }

    @DisplayName("Тестирует поиск книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookDaoJdbc.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найден автор");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует поиск всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        List<Book> actualBookList = bookDaoJdbc.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
        assertEquals(6, actualBookList.size(), "Неверное количество записей");
    }

    @DisplayName("Тестирует поиск книг по названию жанра")
    @Test
    void findAllByGenreNameTest() {
        Book expectedBook = getExistingBook();
        List<Book> actualBookList = bookDaoJdbc.findAllByGenreName("Фэнтези");
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
        assertEquals(3, actualBookList.size(), "Неверное количество записей");
    }

    private static Book getExistingBook() {
        return Book.builder()
                .id(3L)
                .title("Игра престолов")
                .published(LocalDate.parse("1996-08-06"))
                .genre(Genre.builder()
                        .id(2L)
                        .name("Фэнтези")
                        .build())
                .author(Author.builder()
                        .id(2L)
                        .name("Джордж Реймонд Ричард Мартин")
                        .birthday(LocalDate.parse("1948-09-20"))
                        .build())
                .build();
    }
}