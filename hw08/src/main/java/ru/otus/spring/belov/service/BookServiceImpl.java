package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

/**
 * Сервис по работе с книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    /** Репозиторий по работе с книгами */
    private final BookRepository bookRepository;
    /** Репозиторий по работе с жанрами */
    private final GenreService genreService;
    /** Репозиторий по работе с авторами */
    private final AuthorService authorService;

    @Override
    public Book save(String title, String published, String genreId, String authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = Book.builder()
                .title(title)
                .published(LocalDate.parse(published))
                .genre(genre)
                .author(author)
                .build();
        return bookRepository.save(book);
    }

    @Override
    public Book update(String id, String title, String published, String genreId, String authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = findById(id);
        book.setTitle(title);
        book.setPublished(LocalDate.parse(published));
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %s", id)));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAllByGenreName(String genreName) {
        var genre = genreService.findByName(genreName);
        return bookRepository.findAllByGenreId(genre.getId());
    }
}
