package ru.otus.spring.belov.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    private Long id;

    /** ФИО */
    private String name;

    /** Дата рождения */
    private LocalDate birthday;
}
