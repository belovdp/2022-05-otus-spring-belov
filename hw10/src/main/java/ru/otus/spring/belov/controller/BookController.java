package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.service.AuthorService;
import ru.otus.spring.belov.service.BookService;
import ru.otus.spring.belov.service.GenreService;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер работы с книгами
 */
@RestController
@RequiredArgsConstructor
public class BookController {

    /** Сервис работы с книгами */
    private final BookService bookService;
    /** Сервис работы с авторами */
    private final AuthorService authorService;
    /** Сервис работы с жанрами */
    private final GenreService genreService;

    @GetMapping("/books")
    public List<BookDto> getBooks() {
        return bookService.getAll();
    }
}
