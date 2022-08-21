package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.dto.AuthorDto;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.GenreDto;
import ru.otus.spring.belov.dto.mappers.BookMapper;
import ru.otus.spring.belov.exceptions.NotFoundException;
import ru.otus.spring.belov.repositories.AuthorRepository;
import ru.otus.spring.belov.repositories.BookRepository;
import ru.otus.spring.belov.repositories.GenreRepository;

import java.time.LocalDate;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса по работе с книгами")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookMapper mapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Тест сохарения книги")
    @Test
    void saveTest() {
        var expectedBook = getBookForSave();
        var bookGenre = expectedBook.getGenre();
        var bookAuthor = expectedBook.getAuthor();
        when(genreRepository.findById(bookGenre.getId())).thenReturn(of(new Genre()));
        when(authorRepository.findById(bookAuthor.getId())).thenReturn(of(new Author()));
        when(mapper.fromDto(any())).thenReturn(new Book());
        bookService.saveOrUpdate(expectedBook);
        verify(bookRepository).save(any());
    }

    @DisplayName("Тест поиска по идентификатору книги")
    @Test
    void findById() {
        when(bookRepository.findById(1L)).thenReturn(of(Book.builder().build()));
        when(bookRepository.findById(2L)).thenReturn(empty());
        assertThatCode(() -> bookService.getById(1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> bookService.getById(2))
                .isInstanceOf(NotFoundException.class);
    }

    private BookDto getBookForSave() {
        var bookGenre = GenreDto.builder().id(1L).name("Фэнтези").build();
        var bookAuthor = AuthorDto.builder().id(1L).name("Пупкин").build();
        return BookDto.builder()
                .title("testTitle")
                .published(LocalDate.now())
                .genre(bookGenre)
                .author(bookAuthor)
                .build();
    }
}