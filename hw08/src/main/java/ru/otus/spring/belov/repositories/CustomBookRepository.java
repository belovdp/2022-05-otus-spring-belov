package ru.otus.spring.belov.repositories;

import ru.otus.spring.belov.domain.BookComment;

public interface CustomBookRepository {

    /**
     * Сохраняет комментарий
     * @param text   текст комментария
     * @param bookId идентификатор книги
     * @return комментарий
     */
    BookComment saveComment(String text, String bookId);

    /**
     * Обновляет комментарий
     * @param commentId идентификатор комментария
     * @param text      текст комментария
     * @return комментарий
     */
    BookComment updateComment(String commentId, String text);

    /**
     * Удаляет книгу по идентификатору
     * @param commentId идентификатор комментария
     */
    void deleteCommentById(String commentId);
}
