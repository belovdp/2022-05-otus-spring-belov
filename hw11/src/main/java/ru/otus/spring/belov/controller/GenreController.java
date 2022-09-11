package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.belov.dto.GenreDto;
import ru.otus.spring.belov.dto.mappers.GenreMapper;
import ru.otus.spring.belov.repositories.GenreRepository;

/**
 * Контроллер для работы с жанрами
 */
@RestController
@RequiredArgsConstructor
public class GenreController {

    /** Репозиторий по работе с жанрами */
    private final GenreRepository genreRepository;
    /** Маппер для работы с жанрами */
    private final GenreMapper genreMapper;

    /**
     * Возвращает список жанров
     * @return жанры
     */
    @GetMapping("/genres")
    public Flux<GenreDto> getGenres() {
        return genreRepository.findAll()
                .map(genreMapper::toDto);
    }
}
