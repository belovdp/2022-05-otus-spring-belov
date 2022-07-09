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


    @Transactional
    @Override
    public Book save(String title, String published, long genreId, long authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = Book.builder()
                .title(title)
                .published(LocalDate.parse(published))
                .genre(genre)
                .author(author)
                .build();
        return bookRepository.saveOrUpdate(book);
    }

    @Transactional
    @Override
    public Book update(long id, String title, String published, long genreId, long authorId) {
        var genre = genreService.findById(genreId);
        var author = authorService.findById(authorId);
        var book = findById(id);
        book.setTitle(title);
        book.setPublished(LocalDate.parse(published));
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.saveOrUpdate(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAllByGenreName(String genreName) {
        return bookRepository.findAllByGenreName(genreName);
    }
}
