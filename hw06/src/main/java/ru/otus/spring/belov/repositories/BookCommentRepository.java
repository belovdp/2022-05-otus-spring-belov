package ru.otus.spring.belov.repositories;

import ru.otus.spring.belov.domain.BookComment;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий по работе с комментариями для книги
 */
public interface BookCommentRepository {

    /**
     * Сохраняет или обновляет комментарий
     * @param comment комментарий
     * @return комментарий
     */
    BookComment saveOrUpdate(BookComment comment);

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<BookComment> findAll();

    /**
     * Возвращает комментарий по идентификатору
     * @param id идентификатор комментария
     * @return комментарий
     */
    Optional<BookComment> findById(long id);

    /**
     * Удаляет комментарий по идентификатору
     * @param id идентификатор комментария
     */
    void deleteById(long id);
}
