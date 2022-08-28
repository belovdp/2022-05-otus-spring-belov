package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.service.BookCommentService;

import javax.validation.Valid;

/**
 * Контроллер по работе с комментариями
 */
@RestController
@RequiredArgsConstructor
public class BookCommentController {

    /** Сервис по работе с комментариями */
    private final BookCommentService commentService;

    /**
     * Сохраняет комментарий
     * @param bookId идентификатор книги
     * @param comment комментарий
     */
    @PostMapping("/book/{bookId}/comment")
    public void save(@PathVariable Long bookId, @Valid @RequestBody BookComment comment) {
        commentService.save(comment, bookId);
    }
}