package ru.otus.spring.belov.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Жанр
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@ToString
@Getter
public class GenreDto {

    /** Идентификатор */
    @NotNull(message = "Жанр должен быть выбран")
    private String id;

    /** Название жанра */
    private String name;
}
