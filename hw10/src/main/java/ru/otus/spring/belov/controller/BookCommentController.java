package ru.otus.spring.belov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.service.BookCommentService;
import ru.otus.spring.belov.service.BookService;

/**
 * Контроллер по работе с комментариями
 */
@RestController
@RequiredArgsConstructor
public class BookCommentController {

    /** Сервис по работе с комментариями */
    private final BookCommentService commentService;
    /** Сервис по работе с книгами */
    private final BookService bookService;

//    /**
//     * Возвращает форму добавления комментария
//     * @param bookId идентификатор книги
//     * @param model модель данных
//     * @return форма добавления комментария
//     */
//    @GetMapping
//    @RequestMapping("book/{bookId}/comment/new")
//    public String newComment(@PathVariable Long bookId, Model model) {
//        bookService.getById(bookId);
//        model.addAttribute("bookId", bookId);
//        model.addAttribute("comment", new BookComment());
//        return "book/comment/form";
//    }
//
//    /**
//     * Сохраняет комментарий
//     * @param bookId идентификатор книги
//     * @param comment комментарий
//     * @return форма просмотра книги
//     */
//    @PostMapping("book/{bookId}/comment")
//    public String save(@ModelAttribute @PathVariable Long bookId, BookComment comment) {
//        commentService.save(comment, bookId);
//        return "redirect:/book/" + bookId + "/view";
//    }
}