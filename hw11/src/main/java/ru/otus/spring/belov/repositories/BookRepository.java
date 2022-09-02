package ru.otus.spring.belov.repositories;

import com.mongodb.lang.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.belov.domain.Book;

/**
 * Репозиторий по работе с книгами
 */
public interface BookRepository extends ReactiveMongoRepository<Book, String> {


    /**
     * Возвращает все книги без комментариев
     * @return список всех книг без комментариев
     */
    @Query(value = "{}", fields = "{'comments' : 0}")
    @NonNull
    Flux<Book> findAll(final Pageable page);
}
