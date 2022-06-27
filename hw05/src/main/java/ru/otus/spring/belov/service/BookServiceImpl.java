package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.dao.AuthorDao;
import ru.otus.spring.belov.dao.BookDao;
import ru.otus.spring.belov.dao.GenreDao;
import ru.otus.spring.belov.domain.Book;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

/**
 * Сервис по работе с книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    /** DAO по работе с книгами */
    private final BookDao bookDao;
    /** DAO по работе с жанрами */
    private final GenreDao genreDao;
    /** DAO по работе с авторами */
    private final AuthorDao authorDao;

    @Override
    public Book save(String title, String published, long genreId, long authorId) {
        var genre = genreDao.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором %d", genreId)));
        var author = authorDao.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором %d", authorId)));
        var book = Book.builder()
                .title(title)
                .published(LocalDate.parse(published))
                .genre(genre)
                .author(author)
                .build();
        return bookDao.save(book);
    }

    @Override
    public Book update(long id, String title, String published, long genreId, long authorId) {
        var genre = genreDao.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором %d", genreId)));
        var author = authorDao.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором %d", authorId)));
        var book = findById(id);
        book.setTitle(title);
        book.setPublished(LocalDate.parse(published));
        book.setGenre(genre);
        book.setAuthor(author);
        return bookDao.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public Book findById(long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %d", id)));
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findAllByGenreName(String genreName) {
        return bookDao.findAllByGenreName(genreName);
    }
}
