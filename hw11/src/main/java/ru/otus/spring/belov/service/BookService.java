package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Book;

import java.util.List;

/**
 * Сервис по работе с книгами
 */
public interface BookService {

    /**
     * Сохраняет книгу
     * @param title     название книги
     * @param published дата публикации
     * @param genreId   идентификатор жанра
     * @param authorId  идентификатор автора
     * @return книга
     */
    Book save(String title, String published, String genreId, String authorId);

    /**
     * Обновляет книгу
     * @param id        идентификатор записи
     * @param title     название книги
     * @param published дата публикации
     * @param genreId   идентификатор жанра
     * @param authorId  идентификатор автора
     * @return книга
     */
    Book update(String id, String title, String published, String genreId, String authorId);

    /**
     * Удаляет книгу по идентификатору
     * @param id идентификатор книги
     */
    void deleteById(String id);

    /**
     * Возвращает книгу по идентификатору
     * @param id идентификатор книги
     * @return книга
     */
    Book findById(String id);

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
