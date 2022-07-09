package ru.otus.spring.belov.repositories;

import ru.otus.spring.belov.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий по работе с авторами
 */
public interface AuthorRepository {

    /**
     * Сохраняет автора
     * @param author автор
     * @return автор
     */
    Author save(Author author);

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
    Optional<Author> findById(long id);
}
