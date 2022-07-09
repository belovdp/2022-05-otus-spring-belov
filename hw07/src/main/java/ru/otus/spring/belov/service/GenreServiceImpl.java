package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Сервис по работе с жанрами
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    /** Репозиторий по работе с жанрами */
    private final GenreRepository genreRepository;

    @Transactional
    @Override
    public Genre save(String name) {
        var genre = Genre.builder()
                .name(name)
                .build();
        return genreRepository.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> findByNameWithBooksAndComments(String name) {
        var genre = findByName(name);
        genre.ifPresent(genreDO -> Hibernate.initialize(genreDO.getBooks()));
        return genre;
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором %d", id)));
    }
}
