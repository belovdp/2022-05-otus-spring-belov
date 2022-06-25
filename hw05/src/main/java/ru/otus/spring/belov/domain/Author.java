package ru.otus.spring.belov.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Книжный автор
 */
@AllArgsConstructor
@Data
public class Author {
    /** Идентификатор */
    private Long id;
    /** ФИО */
    private String name;
    /** Дата рождения */
    private LocalDate birthday;
}
