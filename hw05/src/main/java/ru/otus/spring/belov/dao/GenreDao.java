package ru.otus.spring.belov.dao;

import ru.otus.spring.belov.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * DAO по работе с жанрами
 */
public interface GenreDao {

    /**
     * Сохраняет жанр
     * @param genre жанр
     * @return жанр
     */
    Genre save(Genre genre);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<Genre> findAll();

    /**
     * Возвращает жанр по названию
     * @param name название
     * @return жанр
     */
    Optional<Genre> findByName(String name);
}
