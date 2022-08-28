package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест репозитория для работы с книгами")
@DataMongoTest
class BookRepositoryTest {

    private static final String EXISTING_BOOK_ID = "3";
    private static final String EXISTING_BOOK_FOR_DELETE_ID = "4";
    private static final String EXISTING_BOOK_COMMENT_ID = "11";
    private static final String EXISTING_BOOK_COMMENT_TEXT = "Ну пожалуй теперь точно всё";
    private static final String EXISTING_BOOK_COMMENT_BOOK_ID = "2";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate template;

    @DisplayName("Тестирует сохранение книги")
    @Test
    void saveTest() {
        var comments = Set.of(new BookComment("test"), new BookComment("Ещё комментарий"));
        var expectedBook = Book.builder()
                .title("test")
                .published(LocalDate.now())
                .genre(template.findById("3", Genre.class))
                .author(template.findById("2", Author.class))
                .comments(comments)
                .build();

        bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует обновление книги")
    @Test
    void updateTest() {
        var comments = Set.of(new BookComment("test"), new BookComment("Ещё комментарий"));
        Book expectedBook = Book.builder()
                .id("2")
                .title("test")
                .published(LocalDate.now())
                .genre(template.findById("2", Genre.class))
                .author(template.findById("2", Author.class))
                .comments(comments)
                .build();
        bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует удаление книги")
    @Test
    void deleteByIdTest() {
        assertThat(bookRepository.findById(EXISTING_BOOK_FOR_DELETE_ID)).isNotEmpty();
        bookRepository.deleteById(EXISTING_BOOK_FOR_DELETE_ID);
        assertThat(bookRepository.findById(EXISTING_BOOK_FOR_DELETE_ID)).isEmpty();
    }

    @DisplayName("Тестирует поиск книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Optional<Book> actualBook = bookRepository.findById(expectedBook.getId());
        assertTrue(actualBook.isPresent(), "Не найдена книга");
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Тестирует поиск всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        // findAll не должен подгружать комментарии
        expectedBook.setComments(Set.of());
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
    }

    @DisplayName("Тестирует поиск книг по названию жанра")
    @Test
    void findAllByGenreNameTest() {
        Book expectedBook = getExistingBook();
        // findAllByGenreId не должен подгружать комментарии
        expectedBook.setComments(Set.of());
        List<Book> actualBookList = bookRepository.findAllByGenreId("2");
        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(expectedBook);
        assertEquals(3, actualBookList.size(), "Неверное количество записей");
    }


    @DisplayName("Тестирует сохранение комментария")
    @Test
    void saveCommentTest() {
        var expectedCommentText = "TextForNewCommentTest";
        var newComment = bookRepository.saveComment(expectedCommentText, EXISTING_BOOK_ID);
        Optional<Book> book = bookRepository.findById(EXISTING_BOOK_ID);
        assertTrue(book.isPresent());
        assertEquals(expectedCommentText, newComment.getText());
        assertThat(book.get().getComments())
                .usingRecursiveFieldByFieldElementComparator()
                .contains(newComment);
    }

    @DisplayName("Тестирует обновление книги")
    @Test
    void updateCommentTest() {
        Optional<Book> book = bookRepository.findById(EXISTING_BOOK_ID);
        assertTrue(book.isPresent());
        var actualComment = book.get().getComments().stream().findAny();
        assertTrue(actualComment.isPresent());
        var expectedCommentText = "TextForUpdateCommentTest";
        assertNotEquals(expectedCommentText, actualComment.get().getText(), "Комментарий уже совпадает, а не должен");
        var updatedCommentId = actualComment.get().getId();

        bookRepository.updateComment(updatedCommentId, expectedCommentText);


        book = bookRepository.findById(EXISTING_BOOK_ID);
        assertTrue(book.isPresent());
        var updatedComment = book.get().getComments()
                .stream()
                .filter(comment -> comment.getId().equals(updatedCommentId))
                .findAny();
        assertTrue(updatedComment.isPresent());
        assertEquals(expectedCommentText, updatedComment.get().getText(), "Комментарий не совпадает");
    }

    @DisplayName("Тестирует удаление комментария")
    @Test
    void deleteCommentByIdTest() {
        Optional<Book> bookBeforeDeleteComment = bookRepository.findById(EXISTING_BOOK_ID);
        assertTrue(bookBeforeDeleteComment.isPresent());
        var anyComment = bookBeforeDeleteComment.get().getComments().stream().findAny();
        assertTrue(anyComment.isPresent());
        var commentIdToDelete = anyComment.get().getId();

        bookRepository.deleteCommentById(commentIdToDelete);

        Optional<Book> bookAfterDeleteComment = bookRepository.findById(EXISTING_BOOK_ID);
        assertTrue(bookAfterDeleteComment.isPresent());
        assertEquals(bookBeforeDeleteComment.get().getComments().size() - 1, bookAfterDeleteComment.get().getComments().size(), "Комментарий не удалён");
        assertFalse(bookAfterDeleteComment.get().getComments()
                .stream()
                .anyMatch(comment -> comment.getId().equals(commentIdToDelete)), "Комментарий не удалён");
    }

    private Book getExistingBook() {
        return bookRepository.findById(EXISTING_BOOK_ID).orElseThrow(() -> new IllegalStateException("Ожидаемая книга не существет"));
    }
}