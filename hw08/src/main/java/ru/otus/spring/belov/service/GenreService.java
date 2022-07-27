package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Genre;

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
    Genre save(String name);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<Genre> findAll();

    /**
     * Возвращает жанр по название
     * @param name название
     * @return жанр
     */
    Genre findByName(String name);

    /**
     * Возвращает жанр по идентификатору
     * @param id идентификатор жанра
     * @return жанр
     */
    Genre findById(long id);
}
