package ru.otus.spring.belov.domain;

import lombok.Data;

/**
 * Жанр
 */
@Data
public class Genre {

    /** Идентификатор */
    private Long id;
    /** Название жанра */
    private String name;
}
