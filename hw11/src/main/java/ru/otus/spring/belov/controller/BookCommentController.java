package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.belov.service.BookCommentService;

/**
 * Контроллер по работе с комментариями
 */
@RequiredArgsConstructor
public class BookCommentController {

    /** Сервис по работе с комментариями */
    private final BookCommentService commentService;

    /**
     * Сохраняет комментарий
     * @param bookId идентификатор книги
     * @param comment комментарий
     */
//    @PostMapping("/book/{bookId}/comment")
//    public void save(@PathVariable Long bookId, @Valid @RequestBody BookComment comment) {
//        commentService.save(comment, bookId);
//    }
}