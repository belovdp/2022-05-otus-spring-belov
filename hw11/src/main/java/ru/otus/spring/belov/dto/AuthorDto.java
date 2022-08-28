package ru.otus.spring.belov.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Книжный автор
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AuthorDto {

    /** Идентификатор */
    @NotNull(message = "Жанр должен быть выбран")
    private String id;

    /** ФИО */
    private String name;
}
