package ru.otus.spring.belov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Комментарий для книги
 */
@AllArgsConstructor
@Getter
@ToString
public class BookCommentDto {

    /** Идентификатор */
    private String id;

    /** Комментарий */
    private String text;
}
