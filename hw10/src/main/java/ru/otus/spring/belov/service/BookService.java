package ru.otus.spring.belov.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.belov.dto.BookDto;
import ru.otus.spring.belov.dto.BookWithCommentsDto;

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
    Page<BookDto> getAll(Pageable pageable);
}
