package ru.otus.spring.belov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Книжный автор
 */
@AllArgsConstructor
@Getter
@ToString
public class AuthorDto {

    /** Идентификатор */
    private Long id;

    /** ФИО */
    private String name;

    /** Дата рождения */
    private LocalDate birthday;
}
