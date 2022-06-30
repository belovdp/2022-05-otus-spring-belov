package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Author;

import java.util.List;

/**
 * Сервис по работе с авторами
 */
public interface AuthorService {

    /**
     * Сохраняет автора
     * @param name     ФИО автора
     * @param birthday день рождения
     * @return автор
     */
    Author save(String name, String birthday);

    /**
     * Возвращает всех авторов
     * @return список всех авторов
     */
    List<Author> findAll();

    /**
     * Возвращает автора содержащего в имени искомую строку
     * @param name строка поиска
     * @return автор содержащий в имени искомую строку
     */
    List<Author> findByNameContaining(String name);

    /**
     * Возвращает автора по идентификатору
     * @param id идентификатор автора
     * @return автор
     */
    Author findById(long id);
}
