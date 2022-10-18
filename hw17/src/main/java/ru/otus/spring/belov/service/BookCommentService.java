package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.BookCommentDto;

/**
 * Сервис по работе с комментариями книг
 */
public interface BookCommentService {

    /**
     * Сохраняет комментарий
     * @param bookComment комментарий к книге
     * @param bookId      идентификатор книги
     * @return комментарий
     */
    BookCommentDto save(BookComment bookComment, long bookId);
}
