package ru.otus.spring.belov.domain;

import lombok.Data;

import java.time.LocalDate;

/**
 * Книга
 */
@Data
public class Book {

    /** Идентификатор */
    private Long id;
    /** Название книги */
    private String title;
    /** Дата публикации книги */
    private LocalDate published;
}
