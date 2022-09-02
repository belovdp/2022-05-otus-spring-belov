package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.spring.belov.dto.AuthorDto;
import ru.otus.spring.belov.dto.mappers.AuthorMapper;
import ru.otus.spring.belov.repositories.AuthorRepository;

/**
 * Контроллер для работы с авторами
 */
@RestController
@RequiredArgsConstructor
public class AuthorController {

    /** Репозиторий работы с авторами */
    private final AuthorRepository authorRepository;
    /** Маппер для работы с авторами */
    private final AuthorMapper authorMapper;

    /**
     * Возвращает список авторов
     * @return авторы
     */
    @GetMapping("/authors")
    public Flux<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .map(authorMapper::toDto);
    }
}
