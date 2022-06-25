package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Genre;

import java.util.List;
import java.util.Optional;

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
    Optional<Genre> findByName(String name);
}
