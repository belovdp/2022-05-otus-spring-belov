package ru.otus.spring.belov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

/**
 * Книга
 */
@AllArgsConstructor
@Getter
@ToString
public class BookWithCommentsDto {

    /** Идентификатор */
    private String id;

    /** Название книги */
    private String title;

    /** Дата публикации книги */
    private LocalDate published;

    /** Жанр */
    private GenreDto genre;

    /** Автор */
    private AuthorDto author;

    /** Комментарии */
    private List<BookCommentDto> comments;
}
