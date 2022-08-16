package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.BookCommentDto;

import java.util.List;

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

    /**
     * Обновляет комментарий
     * @param id   идентификатор записи
     * @param text текст комментария
     * @return комментарий
     */
    BookCommentDto update(long id, String text);

    /**
     * Удаляет книгу по идентификатору
     * @param id идентификатор книги
     */
    void deleteById(long id);

    /**
     * Возвращает комментарии по идентификатору книги
     * @param id идентификатор книги
     * @return комментарии
     */
    List<BookCommentDto> getBookCommentsByBookId(long id);


    /**
     * Возвращает все комментарии
     * @return список всех комментариев
     */
    List<BookCommentDto> getAll();

    /**
     * Возвращает комментарий по идентификатору
     * @param id идентификатор комментария
     * @return комментарий
     */
    BookCommentDto getById(long id);
}
