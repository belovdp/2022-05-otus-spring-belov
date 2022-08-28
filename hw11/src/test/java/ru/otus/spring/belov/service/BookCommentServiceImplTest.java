package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.dto.mappers.BookCommentMapper;
import ru.otus.spring.belov.repositories.BookCommentRepository;
import ru.otus.spring.belov.repositories.BookRepository;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тест сервиса по работе с комментариями")
@ExtendWith(MockitoExtension.class)
class BookCommentServiceImplTest {

    @Mock
    private BookCommentRepository bookCommentRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookCommentMapper mapper;
    @InjectMocks
    private BookCommentServiceImpl bookCommentService;

    @DisplayName("Тест сохарения книги")
    @Test
    void saveTest() {
        var expectedBookComment = getBookCommentForSave();
        var commentBook = expectedBookComment.getBook();
        when(bookRepository.findById(commentBook.getId())).thenReturn(of(commentBook));
        bookCommentService.save(expectedBookComment, commentBook.getId());
        verify(bookCommentRepository).save(argThat(actualSavedBookComment -> {
            assertThat(actualSavedBookComment)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBookComment);
            return true;
        }));
    }

    private BookComment getBookCommentForSave() {
        return BookComment.builder()
                .text("test")
                .book(Book.builder().id(1L).title("test").build())
                .build();
    }
}