package ru.otus.spring.belov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий по работе с жанрами
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {

    /**
     * Возвращает все жанры
     * @return список всех жанров
     */
    List<Genre> findAll();
}
