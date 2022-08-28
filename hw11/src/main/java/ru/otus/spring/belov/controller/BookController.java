package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.belov.service.AuthorService;
import ru.otus.spring.belov.service.BookService;
import ru.otus.spring.belov.service.GenreService;

/**
 * Контроллер работы с книгами
 */
@RequiredArgsConstructor
public class BookController {

    /** Сервис работы с книгами */
    private final BookService bookService;
    /** Сервис работы с авторами */
    private final AuthorService authorService;
    /** Сервис работы с жанрами */
    private final GenreService genreService;

    /**
     * Возвращает список книг
     * @param pageable пагинация и сортировка
     * @return список книг
     */
//    @GetMapping("/books")
//    public Page<BookDto> getBooks(Pageable pageable) {
//        return bookService.getAll(pageable);
//    }

    /**
     * Возвращает книгу
     * @param bookId идентификатор книги
     * @return книга
     */
//    @GetMapping("/book/{bookId}")
//    public BookWithCommentsDto getBook(@PathVariable Long bookId) {
//        return bookService.getById(bookId);
//    }

    /**
     * Удаляет книгу
     * @param bookId идентификатор книги
     */
//    @DeleteMapping("/book/{bookId}")
//    public void deleteBook(@PathVariable Long bookId) {
//        bookService.deleteById(bookId);
//    }

    /**
     * Сохраняет или обновляет книгу
     * @param bookDto книга
     * @return книга
     */
//    @PostMapping("/book")
//    public BookDto saveOrUpdate(@Valid @RequestBody BookDto bookDto) {
//        return bookService.saveOrUpdate(bookDto);
//    }
}
