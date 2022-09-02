package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.belov.domain.Author;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Тест репозитория для работы с авторами")
@DataMongoTest
class AuthorRepositoryTest {

    private static final String EXISTING_AUTHOR_ID = "2";
    private static final String EXISTING_AUTHOR_NAME = "Джордж Реймонд Ричард Мартин";
    private static final LocalDate EXISTING_AUTHOR_BIRTHDAY = LocalDate.parse("1948-09-20");

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Тестирует сохранение записи")
    @Test
    void saveTest() {
        Author expectedAuthor = Author.builder().name("test").birthday(LocalDate.now()).build();
        Mono<Author> monoExpectedAuthor = authorRepository.save(expectedAuthor)
                .flatMap(author -> authorRepository.findById(author.getId()));
        StepVerifier
                .create(monoExpectedAuthor)
                .assertNext(Assertions::assertNotNull)
                .thenRequest(1)
                .assertNext(Assertions::assertNull)
                .expectComplete()
                .verify();
    }

    @DisplayName("Тестирует поиск списка записей")
    @Test
    void findAllTest() {
        Author expectedAuthor = Author.builder()
                .id(EXISTING_AUTHOR_ID)
                .name(EXISTING_AUTHOR_NAME)
                .birthday(EXISTING_AUTHOR_BIRTHDAY)
                .build();
        StepVerifier
                .create(authorRepository.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)  // Predicate on elements
                .consumeRecordedWith(actualAuthorList ->
                        assertThat(actualAuthorList)
                                .usingDefaultElementComparator()
                                .contains(expectedAuthor))
                .verifyComplete();
    }

    @DisplayName("Тестирует поиск записи по id")
    @Test
    void findByIdTest() {
        Author expectedAuthor = Author.builder()
                .id(EXISTING_AUTHOR_ID)
                .name(EXISTING_AUTHOR_NAME)
                .birthday(EXISTING_AUTHOR_BIRTHDAY)
                .build();
        Mono<Author> monoActualAuthor = authorRepository.findById(expectedAuthor.getId());
        StepVerifier
                .create(monoActualAuthor)
                .expectNext(expectedAuthor)
                .expectComplete()
                .verify();
    }
}