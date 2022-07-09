package ru.otus.spring.belov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Книга
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название книги */
    @Column(name = "title", nullable = false)
    private String title;

    /** Дата публикации книги */
    @Column(name = "published", nullable = false)
    private LocalDate published;

    /** Жанр */
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    /** Автор */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
