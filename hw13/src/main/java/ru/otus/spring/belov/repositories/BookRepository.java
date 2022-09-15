package ru.otus.spring.belov.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий по работе с книгами
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Возвращает книгу по идентификатору, подгружая все связи
     * @param id идентификатор книги
     * @return книга со всеми прогруженными связями
     */
    @EntityGraph(value = "book-full")
    @Override
    Optional<Book> findById(Long id);
}
