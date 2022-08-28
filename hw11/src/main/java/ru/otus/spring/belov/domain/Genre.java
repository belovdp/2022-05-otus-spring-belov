package ru.otus.spring.belov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Жанр
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    /** Идентификатор */
    @Id
    private String id;

    /** Название жанра */
    private String name;
}
