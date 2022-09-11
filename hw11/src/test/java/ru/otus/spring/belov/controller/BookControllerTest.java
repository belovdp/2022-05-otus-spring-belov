package ru.otus.spring.belov.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.belov.configuration.PageableWebFluxConfiguration;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.repositories.AuthorRepository;
import ru.otus.spring.belov.repositories.BookRepository;
import ru.otus.spring.belov.repositories.GenreRepository;

import java.time.LocalDate;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(PageableWebFluxConfiguration.class)
class BookControllerTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @Autowired
    private ApplicationContext context;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @DisplayName("Тест получения книги по идентификатору")
    @Test
    void getBook() {
        var book = new Book("2", "sdf", LocalDate.now(), new Genre(), new Author(), new HashSet<>());
        when(bookRepository.findById("2")).thenReturn(Mono.just(book));
        webTestClient.get()
                .uri("/book/{id}", 2)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(book.getId())
                .jsonPath("$.title").isEqualTo(book.getTitle())
                .jsonPath("$.published").isEqualTo(book.getPublished().toString());
    }

    @DisplayName("Тест удаления книги")
    @Test
    void deleteById() {
        webTestClient.delete()
                .uri("/book/{id}", 2)
                .exchange()
                .expectStatus()
                .isOk();
        verify(bookRepository).deleteById("2");
    }

    @DisplayName("Тест получения книг")
    @Test
    void getBooks() {
        when(bookRepository.count()).thenReturn(Mono.just(1L));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(Flux.just(new Book()));
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/books")
                                .queryParam("page", "0")
                                .queryParam("size", "10")
                                .build())
                .exchange()
                .expectStatus()
                .isOk();
        verify(bookRepository).findAll(any(Pageable.class));
    }

    @DisplayName("Тест сохранения")
    @Test
    void saveOrUpdate() {
        var genre = new Genre();
        genre.setId("2");
        var author = new Author();
        author.setId("2");
        var book = new Book("2", "sdbvbv", LocalDate.now(), genre, author, new HashSet<>());
        when(bookRepository.save(any())).thenReturn(Mono.just(book));
        when(authorRepository.findById("2")).thenReturn(Mono.just(author));
        when(genreRepository.findById("2")).thenReturn(Mono.just(genre));
        webTestClient.post()
                .uri("/book")
                .bodyValue(book)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(book.getId())
                .jsonPath("$.title").isEqualTo(book.getTitle())
                .jsonPath("$.published").isEqualTo(book.getPublished().toString());
    }
}