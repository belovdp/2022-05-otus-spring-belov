package ru.otus.spring.belov.domain.relation;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Книга
 */
@Builder
@Data
@ToString(exclude = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-full", includeAllAttributes = true)
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

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<BookComment> comments;
}
