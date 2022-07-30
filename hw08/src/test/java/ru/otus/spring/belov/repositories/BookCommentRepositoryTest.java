package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.BookComment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест репозитория для работы с комментариями")
@DataJpaTest
class BookCommentRepositoryTest {

    private static final String EXISTING_BOOK_COMMENT_ID = "11";
    private static final String EXISTING_BOOK_COMMENT_TEXT = "Ну пожалуй теперь точно всё";
    private static final String EXISTING_BOOK_COMMENT_BOOK_ID = "2";
//    @Autowired
//    private BookCommentRepository bookCommentRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Тестирует сохранение комментария")
    @Test
    void saveTest() {
//        BookComment expectedBookComment = BookComment.builder()
//                .text("test")
////                .book(em.find(Book.class, EXISTING_BOOK_COMMENT_BOOK_ID))
//                .build();
//        bookCommentRepository.save(expectedBookComment);
//        Optional<BookComment> actualBookComment = bookCommentRepository.findById(expectedBookComment.getId());
//        assertTrue(actualBookComment.isPresent(), "Не найден комментарий");
//        assertThat(actualBookComment.get()).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @DisplayName("Тестирует обновление книги")
    @Test
    void updateTest() {
//        BookComment expectedBookComment = BookComment.builder()
//                .id(EXISTING_BOOK_COMMENT_ID)
//                .text("test")
//                .build();
//        bookCommentRepository.save(expectedBookComment);
//        Optional<BookComment> actualBookComment = bookCommentRepository.findById(expectedBookComment.getId());
//        assertTrue(actualBookComment.isPresent(), "Не найден комментарий");
//        assertThat(actualBookComment.get()).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }

    @DisplayName("Тестирует удаление комментария")
    @Test
    void deleteByIdTest() {
//        assertThat(bookCommentRepository.findById(EXISTING_BOOK_COMMENT_ID)).isNotEmpty();
//        bookCommentRepository.deleteById(EXISTING_BOOK_COMMENT_ID);
//        em.flush();
//        assertThat(bookCommentRepository.findById(EXISTING_BOOK_COMMENT_ID)).isEmpty();
    }

    @DisplayName("Тестирует поиск комментария по идентификатору")
    @Test
    void findByIdTest() {
//        BookComment expectedBookComment = BookComment.builder()
//                .id(EXISTING_BOOK_COMMENT_ID)
//                .text(EXISTING_BOOK_COMMENT_TEXT)
////                .book(em.find(Book.class, EXISTING_BOOK_COMMENT_BOOK_ID))
//                .build();
//        Optional<BookComment> actualBook = bookCommentRepository.findById(expectedBookComment.getId());
//        assertTrue(actualBook.isPresent(), "Не найден комментарий");
//        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBookComment);
    }


    @DisplayName("Тестирует поиск всех комментариев")
    @Test
    void findAllTest() {
//        BookComment expectedBookComment = BookComment.builder()
//                .id(EXISTING_BOOK_COMMENT_ID)
//                .text(EXISTING_BOOK_COMMENT_TEXT)
////                .book(em.find(Book.class, EXISTING_BOOK_COMMENT_BOOK_ID))
//                .build();
//        List<BookComment> actualBookList = bookCommentRepository.findAll();
//        assertThat(actualBookList)
//                .usingRecursiveFieldByFieldElementComparator()
//                .contains(expectedBookComment);
//        assertEquals(11, actualBookList.size(), "Неверное количество записей");
    }
}