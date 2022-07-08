package ru.otus.spring.belov.dao;

import ru.otus.spring.belov.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * DAO по работе с книгами
 */
public interface BookDao {

    /**
     * Сохраняет книгу
     * @param book книга
     * @return книга
     */
    Book save(Book book);

    /**
     * Обновляет книгу
     * @param book книга
     * @return книга
     */
    Book update(Book book);

    /**
     * Удаляет книгу по идентификатору
     * @param id идентификатор книги
     */
    void deleteById(long id);

    /**
     * Возвращает книгу по идентификатору
     * @param id идентификатор книги
     * @return книга
     */
    Optional<Book> findById(long id);

    /**
     * Возвращает все книги
     * @return список всех книг
     */
    List<Book> findAll();

    /**
     * Возвращает все книги по названию жанра
     * @param genreName название жанра
     * @return список всех книг по жанру
     */
    List<Book> findAllByGenreName(String genreName);
}
