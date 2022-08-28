package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.belov.service.GenreService;

/**
 * Контроллер для работы с жанрами
 */
@RequiredArgsConstructor
public class GenreController {

    /** Сервис по работе с жанрами */
    private final GenreService genreService;

    /**
     * Возвращает список жанров
     * @return жанры
     */
//    @GetMapping("/genres")
//    public List<GenreDto> getGenres() {
//        return genreService.getAll();
//    }
}
