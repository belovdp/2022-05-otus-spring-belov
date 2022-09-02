package ru.otus.spring.belov.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.belov.domain.Genre;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория для работы с авторами")
@DataMongoTest
class GenreRepositoryTest {

    private static final String EXISTING_GENRE_ID = "2";
    private static final String EXISTING_GENRE_NAME = "Фэнтези";

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("Тестирует сохранение записи")
    @Test
    void saveTest() {
        Genre expectedGenre = Genre.builder().name("test").build();
        Mono<Genre> monoExpectedGenre = genreRepository.save(expectedGenre)
                .flatMap(genre -> genreRepository.findById(genre.getId()));
        StepVerifier
                .create(monoExpectedGenre)
                .expectNext(expectedGenre)
                .expectComplete()
                .verify();
    }

    @DisplayName("Тестирует поиск списка записей")
    @Test
    void findAllTest() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTING_GENRE_ID)
                .name(EXISTING_GENRE_NAME)
                .build();
        StepVerifier
                .create(genreRepository.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)  // Predicate on elements
                .consumeRecordedWith(actualAuthorList ->
                        assertThat(actualAuthorList)
                                .usingDefaultElementComparator()
                                .contains(expectedGenre))
                .verifyComplete();
    }

    @DisplayName("Тестирует поиск записи по id")
    @Test
    void findByIdTest() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTING_GENRE_ID)
                .name(EXISTING_GENRE_NAME)
                .build();
        Mono<Genre> monoActualGenre = genreRepository.findById(expectedGenre.getId());
        StepVerifier
                .create(monoActualGenre)
                .expectNext(expectedGenre)
                .expectComplete()
                .verify();
    }
}