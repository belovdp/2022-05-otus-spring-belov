package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.dto.GenreDto;
import ru.otus.spring.belov.dto.mappers.GenreMapper;
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
    /** Преобразователь сущностей в DTO */
    private final GenreMapper mapper;

    @Override
    public GenreDto save(String name) {
        var genre = Genre.builder()
                .name(name)
                .build();
        return mapper.toDto(genreRepository.save(genre));
    }

    @Override
    public List<GenreDto> findAll() {
        return mapper.toDto(genreRepository.findAll());
    }

    @Override
    public GenreDto getByName(String name) {
        return mapper.toDto(genreRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с именем %s", name))));
    }

    @Override
    public GenreDto getById(long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public Genre findById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором %d", id)));
    }
}
