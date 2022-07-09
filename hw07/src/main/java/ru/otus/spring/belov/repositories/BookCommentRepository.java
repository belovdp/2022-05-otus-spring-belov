package ru.otus.spring.belov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.domain.BookComment;

import java.util.List;

/**
 * Репозиторий по работе с комментариями для книги
 */
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<BookComment> findAll();
}
