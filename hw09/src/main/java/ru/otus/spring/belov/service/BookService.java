package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;

import java.util.List;

/**
 * Сервис по работе с книгами
 */
public interface BookService {

    /**
     * Сохраняет книгу
     * @param book сохраняемая книга
     * @return книга
     */
    BookDto saveOrUpdate(BookDto book);

    /**
     * Обновляет книгу
     * @param id        идентификатор записи
     * @param title     название книги
     * @param published дата публикации
     * @param genreId   идентификатор жанра
     * @param authorId  идентификатор автора
     * @return книга
     */
    BookDto update(long id, String title, String published, long genreId, long authorId);

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
    BookWithCommentsDto getById(long id);

    /**
     * Возвращает все книги
     * @return список всех книг
     */
    List<BookDto> getAll();
}
