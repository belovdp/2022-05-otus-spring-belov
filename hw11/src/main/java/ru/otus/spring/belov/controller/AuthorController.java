package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.dto.AuthorDto;
import ru.otus.spring.belov.service.AuthorService;

import java.util.List;

/**
 * Контроллер для работы с авторами
 */
@RestController
@RequiredArgsConstructor
public class AuthorController {

    /** Сервис с авторами */
    private final AuthorService authorService;

    /**
     * Возвращает список авторов
     * @return
     */
    @GetMapping("/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.getAll();
    }
}
