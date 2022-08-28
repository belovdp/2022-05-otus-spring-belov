package ru.otus.spring.belov.service;

import ru.otus.spring.belov.dto.GenreDto;

import java.util.List;

/**
 * Сервис по работе с жанрами
 */
public interface GenreService {

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<GenreDto> getAll();
}
