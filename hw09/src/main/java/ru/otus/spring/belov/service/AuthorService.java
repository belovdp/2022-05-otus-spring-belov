package ru.otus.spring.belov.service;

import ru.otus.spring.belov.dto.AuthorDto;

import java.util.List;

/**
 * Сервис по работе с авторами
 */
public interface AuthorService {

    /**
     * Возвращает всех авторов
     * @return список всех авторов
     */
    List<AuthorDto> getAll();
}
