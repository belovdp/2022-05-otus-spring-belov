package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;
import ru.otus.spring.belov.dto.mappers.BookMapper;
import ru.otus.spring.belov.exceptions.NotFoundException;
import ru.otus.spring.belov.repositories.AuthorRepository;
import ru.otus.spring.belov.repositories.BookRepository;
import ru.otus.spring.belov.repositories.GenreRepository;

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
    private final GenreRepository genreRepository;
    /** Репозиторий по работе с авторами */
    private final AuthorRepository authorRepository;
    /** Преобразователь сущностей в DTO */
    private final BookMapper mapper;

    @Override
    public BookDto saveOrUpdate(BookDto bookDto) {
        var genre = getGenreById(bookDto.getGenre().getId());
        var author = getAuthorById(bookDto.getAuthor().getId());
        var book = mapper.fromDto(bookDto);
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
                .orElseThrow(() -> new NotFoundException(format("Не найдена книга с идентификатором %d", id)));
        return mapper.toDtoWithComments(book);
    }

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    private Genre getGenreById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден жанр с идентификатором %d", id)));
    }

    private Author getAuthorById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден автор с идентификатором %d", id)));
    }

    private Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найдена книга с идентификатором %d", id)));
    }
}
