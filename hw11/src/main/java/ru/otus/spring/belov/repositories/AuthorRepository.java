package ru.otus.spring.belov.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.belov.domain.Author;

/**
 * Репозиторий по работе с авторами
 */
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Flux<Author> findAll();
}
