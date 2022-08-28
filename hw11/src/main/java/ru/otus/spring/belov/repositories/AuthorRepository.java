package ru.otus.spring.belov.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.belov.domain.Author;

import java.util.List;

/**
 * Репозиторий по работе с авторами
 */
public interface AuthorRepository extends MongoRepository<Author, String> {

    /**
     * Возвращает автора содержащего в имени искомую строку
     * @param name строка поиска
     * @return автор содержащий в имени искомую строку
     */
    List<Author> findByNameContainingIgnoreCase(String name);
}
