package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.GenreDto;
import ru.otus.spring.belov.service.GenreService;

import java.util.List;

/**
 * Контроллер для работы с жанрами
 */
@RestController
@RequiredArgsConstructor
public class GenreController {

    /** Сервис по работе с жанрами */
    private final GenreService genreService;

    /**
     * Возвращает список жанров
     * @return жанры
     */
    @GetMapping("/genres")
    public List<GenreDto> getGenres() {
        return genreService.getAll();
    }
}
