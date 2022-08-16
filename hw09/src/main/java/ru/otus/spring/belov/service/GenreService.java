package ru.otus.spring.belov.service;

import ru.otus.spring.belov.dto.GenreDto;

import java.util.List;

/**
 * Сервис по работе с жанрами
 */
public interface GenreService {

    /**
     * Сохраняет жанр
     * @param name название жанра
     * @return жанр
     */
    GenreDto save(String name);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<GenreDto> getAll();

    /**
     * Возвращает жанр по название
     * @param name название
     * @return жанр
     */
    GenreDto getByName(String name);

    /**
     * Возвращает жанр по идентификатору
     * @param id идентификатор жанра
     * @return жанр
     */
    GenreDto getById(long id);
}
