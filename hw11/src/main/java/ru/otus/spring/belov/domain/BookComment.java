package ru.otus.spring.belov.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Комментарий для книги
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class BookComment {

    /** Идентификатор */
    @Id
    private String id;

    /** Комментарий */
    private String text;

    /**
     * Конструктор
     * @param text текст комментария
     */
    public BookComment(String text) {
        this.id = new ObjectId().toString();
        this.text = text;
    }
}
