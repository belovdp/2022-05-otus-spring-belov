package ru.otus.spring.belov.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Жанр
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "books")
@Table(name = "genres")
public class Genre {

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название жанра */
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Book> books;
}
