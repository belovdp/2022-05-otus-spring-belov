package ru.otus.spring.belov.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Жанр
 */
@AllArgsConstructor
@ToString
public class GenreDto {

    /** Идентификатор */
    private Long id;

    /** Название жанра */
    private String name;
}
