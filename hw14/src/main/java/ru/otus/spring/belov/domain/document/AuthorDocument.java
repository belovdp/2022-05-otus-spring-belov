package ru.otus.spring.belov.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Книжный автор
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class AuthorDocument {

    /** Идентификатор */
    @Id
    private String id;

    /** ФИО */
    private String name;

    /** Дата рождения */
    private LocalDate birthday;

    /** id до миграции */
    private Long oldId;
}
