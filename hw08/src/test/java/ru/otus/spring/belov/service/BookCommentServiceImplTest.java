package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.belov.domain.BookComment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@DisplayName("Тест сервиса по работе с комментариями")
@ExtendWith(MockitoExtension.class)
class BookCommentServiceImplTest {

    @Mock
    private BookService bookService;
    @InjectMocks
    private BookCommentServiceImpl bookCommentService;

    @DisplayName("Тест сохарения книги")
    @Test
    void saveTest() {
        var expectedBookComment = getBookCommentForSave();
//        var commentBook = expectedBookComment.getBook();
//        when(bookService.findById(commentBook.getId())).thenReturn(commentBook);
//        bookCommentService.save(expectedBookComment.getText(), commentBook.getId());
//        verify(bookCommentRepository).save(argThat(actualSavedBookComment -> {
//            assertThat(actualSavedBookComment)
//                    .usingRecursiveComparison()
//                    .isEqualTo(expectedBookComment);
//            return true;
//        }));
    }

    @DisplayName("Тест обновления книги")
    @Test
    void updateTest() {
        var expectedBookComment = getBookCommentForSave();
//        expectedBookComment.setId(3L);
//        when(bookCommentRepository.findById(expectedBookComment.getId()))
//                .thenReturn(of(BookComment.builder().id(expectedBookComment.getId()).book(expectedBookComment.getBook()).build()));
//        bookCommentService.update(expectedBookComment.getId(), expectedBookComment.getText());
//        verify(bookCommentRepository).save(argThat(actualSavedBookComment -> {
//            assertThat(actualSavedBookComment)
//                    .usingRecursiveComparison()
//                    .isEqualTo(expectedBookComment);
//            return true;
//        }));
    }

    @DisplayName("Тест поиска по идентификатору комментария")
    @Test
    void findById() {
//        when(bookCommentRepository.findById(1L)).thenReturn(of(BookComment.builder().build()));
//        when(bookCommentRepository.findById(2L)).thenReturn(empty());
//        assertThatCode(() -> bookCommentService.findById(1))
//                .doesNotThrowAnyException();
//        assertThatThrownBy(() -> bookCommentService.findById(2))
//                .isInstanceOf(IllegalArgumentException.class);
    }

    private BookComment getBookCommentForSave() {
        return BookComment.builder()
                .text("test")
//                .book(Book.builder().id(1L).title("test").build())
                .build();
    }
}