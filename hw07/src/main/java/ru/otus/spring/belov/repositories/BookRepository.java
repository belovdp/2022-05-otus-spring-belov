package ru.otus.spring.belov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.domain.Book;

import java.util.List;

/**
 * Репозиторий по работе с книгами
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Возвращает все книги
     * @return список всех книг
     */
    List<Book> findAll();

    /**
     * Возвращает все книги по названию жанра
     * @param genreName название жанра
     * @return список всех книг по жанру
     */
    List<Book> findAllByGenreName(String genreName);
}
