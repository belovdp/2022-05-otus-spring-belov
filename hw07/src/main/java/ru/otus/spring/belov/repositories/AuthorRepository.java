package ru.otus.spring.belov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.domain.Author;

import java.util.List;

/**
 * Репозиторий по работе с авторами
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Возвращает всех авторов
     * @return список всех авторов
     */
    List<Author> findAll();

    /**
     * Возвращает автора содержащего в имени искомую строку
     * @param name строка поиска
     * @return автор содержащий в имени искомую строку
     */
    List<Author> findByNameContainingIgnoreCase(String name);
}
