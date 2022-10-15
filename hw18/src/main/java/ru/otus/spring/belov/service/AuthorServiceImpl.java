package ru.otus.spring.belov.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.dto.AuthorDto;
import ru.otus.spring.belov.dto.mappers.AuthorMapper;
import ru.otus.spring.belov.repositories.AuthorRepository;

import java.util.List;

/**
 * Сервис по работе с автоарми
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    /** Репозиторий По работе с авторами */
    private final AuthorRepository authorRepository;
    /** Преобразователь сущностей в DTO */
    private final AuthorMapper mapper;

    @HystrixCommand
    @Override
    public List<AuthorDto> getAll() {
        return mapper.toDto(authorRepository.findAll());
    }
}
