package ru.otus.spring.belov.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Книжный автор
 */
@Builder
@Data
public class Author {
    /** Идентификатор */
    private Long id;
    /** ФИО */
    private String name;
    /** Дата рождения */
    private LocalDate birthday;
}
