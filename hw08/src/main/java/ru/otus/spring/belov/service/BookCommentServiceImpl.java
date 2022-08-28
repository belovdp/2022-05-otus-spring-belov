package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.repositories.BookRepository;

import java.util.Set;

import static java.lang.String.format;

/**
 * Сервис по работе с комментариями
 */
@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    /** Репозиторий по работе с книгами */
    private final BookRepository bookRepository;

    @Override
    public BookComment save(String text, String bookId) {
        return bookRepository.saveComment(text, bookId);
    }

    @Override
    public BookComment update(String commentId, String text) {
        return bookRepository.updateComment(commentId, text);
    }

    @Override
    public void deleteById(String commentId) {
        bookRepository.deleteCommentById(commentId);
    }

    @Override
    public Set<BookComment> findBookCommentsByBookId(String id) {
        var book = findBookById(id);
        return book.getComments();
    }

    private Book findBookById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %s", bookId)));
    }
}
