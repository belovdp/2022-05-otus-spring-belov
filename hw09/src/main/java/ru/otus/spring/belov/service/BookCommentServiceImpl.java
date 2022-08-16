package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.BookCommentDto;
import ru.otus.spring.belov.dto.mappers.BookCommentMapper;
import ru.otus.spring.belov.repositories.BookCommentRepository;
import ru.otus.spring.belov.repositories.BookRepository;

import static java.lang.String.format;

/**
 * Сервис по работе с комментариями
 */
@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    /** Репозиторий по работе с книгами */
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;
    /** Преобразователь сущностей в DTO */
    private final BookCommentMapper mapper;

    @Override
    public BookCommentDto save(BookComment bookComment, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %d", bookId)));
        bookComment.setBook(book);
        return mapper.toDto(bookCommentRepository.save(bookComment));
    }
}
