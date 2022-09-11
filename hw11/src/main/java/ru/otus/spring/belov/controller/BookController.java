package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;
import ru.otus.spring.belov.dto.mappers.BookMapper;
import ru.otus.spring.belov.exceptions.NotFoundException;
import ru.otus.spring.belov.repositories.AuthorRepository;
import ru.otus.spring.belov.repositories.BookRepository;
import ru.otus.spring.belov.repositories.GenreRepository;

import javax.validation.Valid;

import static java.lang.String.format;

/**
 * Контроллер работы с книгами
 */
@RestController
@RequiredArgsConstructor
public class BookController {

    /** Репозиторий работы с книгами */
    private final BookRepository bookRepository;
    /** Репозиторий работы с авторами */
    private final AuthorRepository authorRepository;
    /** Репозиторий работы с жанрами */
    private final GenreRepository genreRepository;
    /** Маппер для работы с книгами */
    private final BookMapper bookMapper;

    /**
     * Возвращает список книг
     * @return список книг
     */
    @GetMapping("/books")
    public Mono<PageImpl<BookDto>> getBooks(Pageable pageable) {
        return bookRepository.count()
                .flatMap(booksCount -> this.bookRepository.findAll(pageable)
                        .map(bookMapper::toDto)
                        .collectList()
                        .map(books -> new PageImpl<>(books, pageable, booksCount)));
    }

    /**
     * Возвращает книгу
     * @param bookId идентификатор книги
     * @return книга
     */
    @GetMapping("/book/{bookId}")
    public Mono<BookWithCommentsDto> getBook(@PathVariable String bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException(format("Не найдена книга с идентификатором %s", bookId))))
                .map(bookMapper::toDtoWithComments);
    }

    /**
     * Удаляет книгу
     * @param bookId идентификатор книги
     */
    @DeleteMapping("/book/{bookId}")
    public Mono<Void> deleteBook(@PathVariable String bookId) {
        return bookRepository.deleteById(bookId);
    }

    /**
     * Сохраняет или обновляет книгу
     * @param bookDto книга
     * @return книга
     */
    @PostMapping("/book")
    public Mono<BookDto> saveOrUpdate(@Valid @RequestBody BookDto bookDto) {
        return Mono.just(bookDto)
                .map(bookMapper::fromDto)
                .flatMap(book -> genreRepository.findById(bookDto.getGenre().getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(format("Не найдена жанр с идентификатором %s", bookDto.getGenre().getId()))))
                        .map(genre -> {
                            book.setGenre(genre);
                            return book;
                        }))
                .flatMap(book -> authorRepository.findById(bookDto.getAuthor().getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(format("Не найдена автор с идентификатором %s", bookDto.getAuthor().getId()))))
                        .map(author -> {
                            book.setAuthor(author);
                            return book;
                        }))
                .flatMap(bookRepository::save)
                .map(bookMapper::toDto);
    }

    /**
     * Сохраняет комментарий
     * @param bookId  идентификатор книги
     * @param comment комментарий
     */
    @PostMapping("/book/{bookId}/comment")
    public Mono<Void> save(@PathVariable String bookId, @Valid @RequestBody BookComment comment) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException(format("Не найдена книга с идентификатором %s", bookId))))
                .map(book -> {
                    book.getComments().add(comment);
                    return book;
                }).flatMap(bookRepository::save)
                .then();
    }
}
