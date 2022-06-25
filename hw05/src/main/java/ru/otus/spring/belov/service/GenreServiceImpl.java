package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.dao.GenreDao;
import ru.otus.spring.belov.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Сервис по работе с жанрами
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    /** DAO по работе с жанрами */
    private final GenreDao genreDao;

    @Override
    public Genre save(String name) {
        var genre = Genre.builder()
                .name(name)
                .build();
        return genreDao.save(genre);
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreDao.findByName(name);
    }
}
