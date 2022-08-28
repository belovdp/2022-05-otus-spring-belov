package ru.otus.spring.belov.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.belov.domain.Genre;

import java.util.Optional;

/**
 * Репозиторий по работе с жанрами
 */
public interface GenreRepository extends MongoRepository<Genre, String> {

    /**
     * Возвращает жанр по названию
     * @param name название
     * @return жанр
     */
    Optional<Genre> findByName(String name);
}
