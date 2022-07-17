package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.BookComment;

import java.util.List;

/**
 * Сервис по работе с комментариями книг
 */
public interface BookCommentService {

    /**
     * Сохраняет комментарий
     * @param text   текст комментария
     * @param bookId идентификатор книги
     * @return комментарий
     */
    BookComment save(String text, long bookId);

    /**
     * Обновляет комментарий
     * @param id   идентификатор записи
     * @param text текст комментария
     * @return комментарий
     */
    BookComment update(long id, String text);

    /**
     * Удаляет книгу по идентификатору
     * @param id идентификатор книги
     */
    void deleteById(long id);

    /**
     * Возвращает комментарий по идентификатору
     * @param id идентификатор комментария
     * @return комментарий
     */
    BookComment findById(long id);

    /**
     * Возвращает комментарии по идентификатору книги
     * @param id идентификатор книги
     * @return комментарии
     */
    List<BookComment> findBookCommentsByBookId(long id);


    /**
     * Возвращает все комментарии
     * @return список всех комментариев
     */
    List<BookComment> findAll();
}
