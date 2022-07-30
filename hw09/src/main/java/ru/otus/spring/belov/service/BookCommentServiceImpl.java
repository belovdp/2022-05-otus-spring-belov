package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.BookCommentDto;
import ru.otus.spring.belov.dto.mappers.BookCommentMapper;
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
    /** Преобразователь сущностей в DTO */
    private final BookCommentMapper mapper;


    @Override
    public BookCommentDto save(String text, long bookId) {
        var book = bookService.findById(bookId);
        var bookComment = BookComment.builder()
                .text(text)
                .book(book)
                .build();
        return mapper.toDto(bookCommentRepository.save(bookComment));
    }

    @Override
    public BookCommentDto update(long id, String text) {
        var bookComment = findById(id);
        bookComment.setText(text);
        return mapper.toDto(bookCommentRepository.save(bookComment));
    }

    @Override
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }


    @Override
    public List<BookCommentDto> getBookCommentsByBookId(long id) {
        var book = bookService.findById(id);
        return mapper.toDto(book.getComments());
    }

    @Override
    public List<BookCommentDto> getAll() {
        return mapper.toDto(bookCommentRepository.findAll());
    }

    @Override
    public BookCommentDto getById(long id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public BookComment findById(long id) {
        return bookCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором %d", id)));
    }
}
