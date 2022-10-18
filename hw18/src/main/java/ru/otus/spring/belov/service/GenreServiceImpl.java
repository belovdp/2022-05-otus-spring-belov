package ru.otus.spring.belov.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.dto.GenreDto;
import ru.otus.spring.belov.dto.mappers.GenreMapper;
import ru.otus.spring.belov.repositories.GenreRepository;

import java.util.List;

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

    @HystrixCommand
    @Override
    public List<GenreDto> getAll() {
        return mapper.toDto(genreRepository.findAll());
    }
}
