package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.domain.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория для работы с книгами")
@DataMongoTest
class BookRepositoryTest {

    private static final String EXISTING_BOOK_ID = "3";
    private static final String EXISTING_BOOK_FOR_DELETE_ID = "4";
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

        Mono<Book> monoExpectedBook = bookRepository.save(expectedBook)
                .flatMap(book -> bookRepository.findById(book.getId()));
        StepVerifier
                .create(monoExpectedBook)
                .expectNext(expectedBook)
                .expectComplete()
                .verify();
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

        Mono<Book> monoExpectedBook = bookRepository.save(expectedBook)
                .flatMap(book -> bookRepository.findById(book.getId()));
        StepVerifier
                .create(monoExpectedBook)
                .expectNext(expectedBook)
                .expectComplete()
                .verify();
    }

    @DisplayName("Тестирует удаление книги")
    @Test
    void deleteByIdTest() {
        Mono<Book> monoDelete = bookRepository.deleteById(EXISTING_BOOK_FOR_DELETE_ID)
                .then(bookRepository.findById(EXISTING_BOOK_FOR_DELETE_ID));

        StepVerifier
                .create(bookRepository.findById(EXISTING_BOOK_FOR_DELETE_ID))
                .assertNext(Assertions::assertNotNull)
                .expectComplete()
                .verify();
        StepVerifier
                .create(monoDelete)
                .expectComplete()
                .verify();
    }

    @DisplayName("Тестирует поиск книги по идентификатору")
    @Test
    void findByIdTest() {
        Book expectedBook = getExistingBook();
        Mono<Book> monoActualBook = bookRepository.findById(expectedBook.getId());
        StepVerifier
                .create(monoActualBook)
                .expectNext(expectedBook)
                .expectComplete()
                .verify();
    }

    @DisplayName("Тестирует поиск всех записей")
    @Test
    void findAllTest() {
        Book expectedBook = getExistingBook();
        // findAll не должен подгружать комментарии
        expectedBook.setComments(Set.of());
        StepVerifier
                .create(bookRepository.findAll(Pageable.ofSize(10)))
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)  // Predicate on elements
                .consumeRecordedWith(actualBookList ->
                        assertThat(actualBookList)
                                .usingRecursiveFieldByFieldElementComparator()
                                .contains(expectedBook)
                )
                .verifyComplete();
    }

    private Book getExistingBook() {
        return template.findById(EXISTING_BOOK_ID, Book.class);
    }
}