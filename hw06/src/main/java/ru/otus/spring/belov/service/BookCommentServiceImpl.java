package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.repositories.BookCommentRepository;

import java.util.List;

import static java.lang.String.format;

/**
 * Сервис по работе с комментариями
 */
@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    /** Репозиторий по работе с книгами */
    private final BookCommentRepository bookCommentRepository;
    private final BookService bookService;


    @Transactional
    @Override
    public BookComment save(String text, long bookId) {
        var book = bookService.findById(bookId);
        var bookComment = BookComment.builder()
                .text(text)
                .book(book)
                .build();
        return bookCommentRepository.saveOrUpdate(bookComment);
    }

    @Transactional
    @Override
    public BookComment update(long id, String text) {
        var bookComment = findById(id);
        bookComment.setText(text);
        return bookCommentRepository.saveOrUpdate(bookComment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public BookComment findById(long id) {
        return bookCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден комментарий с идентификатором %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> findAll() {
        return bookCommentRepository.findAll();
    }
}