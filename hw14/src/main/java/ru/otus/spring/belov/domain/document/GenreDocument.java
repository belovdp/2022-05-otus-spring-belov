package ru.otus.spring.belov.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Жанр
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "genres")
public class GenreDocument {

    /** Идентификатор */
    @Id
    private String id;

    /** Название жанра */
    private String name;

    /** id до миграции */
    private Long oldId;
}
