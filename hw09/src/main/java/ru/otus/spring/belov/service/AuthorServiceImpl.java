package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.dto.AuthorDto;
import ru.otus.spring.belov.dto.mappers.AuthorMapper;
import ru.otus.spring.belov.repositories.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

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

    @Override
    public AuthorDto save(String name, String birthday) {
        var author = Author.builder()
                .name(name)
                .birthday(LocalDate.parse(birthday))
                .build();
        return mapper.toDto(authorRepository.save(author));
    }

    @Override
    public List<AuthorDto> getAll() {
        return mapper.toDto(authorRepository.findAll());
    }

    @Override
    public List<AuthorDto> getByNameContaining(String name) {
        return mapper.toDto(authorRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public AuthorDto getById(long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public Author findById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором %d", id)));
    }
}
