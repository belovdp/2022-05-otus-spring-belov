package ru.otus.spring.belov.domain.relation;

import lombok.*;

import javax.persistence.*;

/**
 * Комментарий для книги
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "book")
@Table(name = "book_comments")
public class BookComment {

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Комментарий */
    @Column(name = "text", nullable = false)
    private String text;

    /** Комментируемая книга */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
}
