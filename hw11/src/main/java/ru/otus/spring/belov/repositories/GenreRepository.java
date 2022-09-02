package ru.otus.spring.belov.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.belov.domain.Genre;

/**
 * Репозиторий по работе с жанрами
 */
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
