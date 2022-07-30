package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;
import ru.otus.spring.belov.dto.mappers.BookMapper;
import ru.otus.spring.belov.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

/**
 * Сервис по работе с книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    /** Репозиторий по работе с книгами */
    private final BookRepository bookRepository;
    /** Репозиторий по работе с жанрами */
    private final GenreService genreService;
    /** Репозиторий по работе с авторами */
    private final AuthorService authorService;
    /** Преобразователь сущностей в DTO */
    private final BookMapper mapper;

    @Override
    public BookDto save(String title, String published, long genreId, long authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = Book.builder()
                .title(title)
                .published(LocalDate.parse(published))
                .genre(genre)
                .author(author)
                .build();
        return mapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto update(long id, String title, String published, long genreId, long authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = findById(id);
        book.setTitle(title);
        book.setPublished(LocalDate.parse(published));
        book.setGenre(genre);
        book.setAuthor(author);
        return mapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookWithCommentsDto getById(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %d", id)));
        return mapper.toDtoWithComments(book);
    }

    @Override
    public Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %d", id)));
    }

    @Override
    public List<BookDto> getAll() {
        return mapper.toDto(bookRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllByGenreName(String genreName) {
        return mapper.toDto(bookRepository.findAllByGenreName(genreName));
    }
}
