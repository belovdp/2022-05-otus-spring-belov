package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.belov.service.AuthorService;

/**
 * Контроллер для работы с авторами
 */
@RequiredArgsConstructor
public class AuthorController {

    /** Сервис с авторами */
    private final AuthorService authorService;

    /**
     * Возвращает список авторов
     * @return
     */
//    @GetMapping("/authors")
//    public List<AuthorDto> getAuthors() {
//        return authorService.getAll();
//    }
}
