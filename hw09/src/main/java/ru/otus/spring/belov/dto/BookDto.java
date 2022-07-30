package ru.otus.spring.belov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Книга
 */
@AllArgsConstructor
@Getter
@ToString
public class BookDto {

    /** Идентификатор */
    private Long id;

    /** Название книги */
    private String title;

    /** Дата публикации книги */
    private LocalDate published;

    // TODO нужно ли мне вот это?
    /** Жанр */
    private GenreDto genre;

    // TODO нужно ли мне вот это?
    /** Автор */
    private AuthorDto author;
}
