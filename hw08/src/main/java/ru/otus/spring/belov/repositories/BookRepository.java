package ru.otus.spring.belov.repositories;

import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.belov.domain.Book;

import java.util.List;

/**
 * Репозиторий по работе с книгами
 */
public interface BookRepository extends MongoRepository<Book, String>, CustomBookRepository {


    /**
     * Возвращает все книги без комментариев
     * @return список всех книг без комментариев
     */
    @Query(value = "{}", fields = "{'comments' : 0}")
    @NonNull
    List<Book> findAll();

    /**
     * Возвращает все книги по идентификатору жанра без комментариев
     * @param genreName название жанра
     * @return список всех книг по жанру без комментариев
     */
    @Query(fields = "{'comments' : 0}")
    List<Book> findAllByGenreId(String genreName);
}
