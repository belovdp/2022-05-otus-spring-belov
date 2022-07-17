package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.repositories.GenreRepository;

import java.util.List;

import static java.lang.String.format;

/**
 * Сервис по работе с жанрами
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    /** Репозиторий по работе с жанрами */
    private final GenreRepository genreRepository;

    @Override
    public Genre save(String name) {
        var genre = Genre.builder()
                .name(name)
                .build();
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с именем %s", name)));
    }

    @Override
    public Genre findById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором %d", id)));
    }
}
