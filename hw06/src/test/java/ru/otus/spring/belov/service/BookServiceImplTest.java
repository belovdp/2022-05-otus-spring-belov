package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.belov.repositories.BookRepository;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.Genre;

import java.time.LocalDate;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса по работе с книгами")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreService genreService;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Тест сохарения книги")
    @Test
    void saveTest() {
        var expectedBook = getBookForSave();
        var bookGenre = expectedBook.getGenre();
        var bookAuthor = expectedBook.getAuthor();
        when(genreService.findById(bookGenre.getId())).thenReturn(bookGenre);
        when(authorService.findById(bookAuthor.getId())).thenReturn(bookAuthor);
        bookService.save(expectedBook.getTitle(), expectedBook.getPublished().toString(), bookGenre.getId(), bookAuthor.getId());
        verify(bookRepository).saveOrUpdate(argThat(actualSavedBook -> {
            assertThat(actualSavedBook)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBook);
            return true;
        }));
    }

    @DisplayName("Тест обновления книги")
    @Test
    void updateTest() {
        var expectedBook = getBookForSave();
        expectedBook.setId(2L);
        var bookGenre = expectedBook.getGenre();
        var bookAuthor = expectedBook.getAuthor();
        when(genreService.findById(bookGenre.getId())).thenReturn(bookGenre);
        when(authorService.findById(bookAuthor.getId())).thenReturn(bookAuthor);
        when(bookRepository.findById(expectedBook.getId()))
                .thenReturn(of(Book.builder().id(expectedBook.getId()).build()));
        bookService.update(2, expectedBook.getTitle(), expectedBook.getPublished().toString(), bookGenre.getId(), bookAuthor.getId());
        verify(bookRepository).saveOrUpdate(argThat(actualSavedBook -> {
            assertThat(actualSavedBook)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBook);
            return true;
        }));
    }

    @DisplayName("Тест поиска по идентификатору книги")
    @Test
    void findById() {
        when(bookRepository.findById(1)).thenReturn(of(Book.builder().build()));
        when(bookRepository.findById(2)).thenReturn(empty());
        assertThatCode(() -> bookService.findById(1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> bookService.findById(2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Book getBookForSave() {
        var bookGenre = Genre.builder().id(1L).name("Фэнтези").build();
        var bookAuthor = Author.builder().id(1L).name("Пупкин").birthday(LocalDate.now()).build();
        return Book.builder()
                .title("testTitle")
                .published(LocalDate.now())
                .genre(bookGenre)
                .author(bookAuthor)
                .build();
    }
}