package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.BookComment;

import java.util.Set;

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
    BookComment save(String text, String bookId);

    /**
     * Обновляет комментарий
     * @param commentId идентификатор комментария
     * @param text      текст комментария
     * @return комментарий
     */
    BookComment update(String commentId, String text);

    /**
     * Удаляет книгу по идентификатору
     * @param commentId идентификатор комментария
     */
    void deleteById(String commentId);

    /**
     * Возвращает комментарии по идентификатору книги
     * @param id идентификатор книги
     * @return комментарии
     */
    Set<BookComment> findBookCommentsByBookId(String id);
}
