package ru.otus.spring.belov.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Книга
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
@ToString(exclude = "comments")
public class Book {

    /** Идентификатор */
    @Id
    private String id;

    /** Название книги */
    private String title;

    /** Дата публикации книги */
    private LocalDate published;

    /** Жанр */
    @DBRef
    private Genre genre;

    /** Автор */
    @DBRef
    private Author author;

    private Set<BookComment> comments = new HashSet<>();
}
