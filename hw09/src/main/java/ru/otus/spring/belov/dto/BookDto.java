package ru.otus.spring.belov.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Книга
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookDto {

    /** Идентификатор */
    private Long id;

    /** Название книги */
    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 5, message = "Минимальное количество символов в названии: 5")
    private String title;

    /** Дата публикации книги */
    @NotNull(message = "Дата не может быть пустой")
    private LocalDate published;

    /** Жанр */
    @Valid
    private GenreDto genre;

    /** Автор */
    @Valid
    private AuthorDto author;
}
