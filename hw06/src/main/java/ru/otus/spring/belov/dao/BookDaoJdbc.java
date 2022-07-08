package ru.otus.spring.belov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO по работе с книгами через JDBC
 */
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    /** Запрос на вставку новой книги */
    private static final String INSERT_BOOK = "INSERT INTO books (title, published, genre_id, author_id) VALUES (:title, :published, :genreId, :authorId)";
    /** Запрос на получение всех книг с авторами и жанрами */
    private static final String SELECT_BOOKS = "SELECT b.id, b.title, b.published, " +
            "b.genre_id, g.name as genreName, b.author_id, a.name as authorName, a.birthday " +
            "FROM books b LEFT JOIN authors a ON b.author_id = a.id LEFT JOIN genres g ON b.genre_id = g.id";
    /** Запрос на поиск книги по идентификатору */
    private static final String SELECT_BOOK_BY_ID = "SELECT b.id, b.title, b.published, " +
            "b.genre_id, g.name as genreName, b.author_id, a.name as authorName, a.birthday " +
            "FROM books b LEFT JOIN authors a ON b.author_id = a.id LEFT JOIN genres g ON b.genre_id = g.id " +
            "WHERE b.id = :id";
    /** Запрос на поиск книги по жанру */
    private static final String SELECT_BOOK_BY_GENRE_NAME = "SELECT b.id, b.title, b.published, " +
            "b.genre_id, g.name as genreName, b.author_id, a.name as authorName, a.birthday " +
            "FROM books b LEFT JOIN authors a ON b.author_id = a.id LEFT JOIN genres g ON b.genre_id = g.id " +
            "WHERE g.name = :genreName";
    /** Запрос на обновление книги */
    private static final String UPDATE_BOOK = "UPDATE books " +
            "SET title = :title, published = :published, author_id = :authorId, genre_id = :genreId " +
            "WHERE id = :id";
    /** Запрос на поиск книги по жанру */
    private static final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = :id";
    /** Маппер книги */
    private final BookMapper bookMapper = new BookMapper();
    /** Компонент для работы с jdbc */
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Book save(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        var params = Map.of(
                "title", book.getTitle(),
                "published", book.getPublished(),
                "genreId", book.getGenre().getId(),
                "authorId", book.getAuthor().getId()
        );
        jdbc.update(INSERT_BOOK, new MapSqlParameterSource(params), keyHolder);
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    @Override
    public Book update(Book book) {
        var params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "published", book.getPublished(),
                "genreId", book.getGenre().getId(),
                "authorId", book.getAuthor().getId()
        );
        jdbc.update(UPDATE_BOOK, params);
        return book;
    }

    @Override
    public void deleteById(long id) {
        jdbc.update(DELETE_BOOK_BY_ID, Map.of("id", id));
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(jdbc.query(SELECT_BOOK_BY_ID, Map.of("id", id), rs -> rs.next() ? bookMapper.mapRow(rs, 1) : null));
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query(SELECT_BOOKS, bookMapper);
    }

    @Override
    public List<Book> findAllByGenreName(String genreName) {
        return jdbc.query(SELECT_BOOK_BY_GENRE_NAME, Map.of("genreName", genreName), bookMapper);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("title");
            LocalDate published = resultSet.getDate("published").toLocalDate();
            var author = mapRowAuthor(resultSet);
            var genre = mapRowGenre(resultSet);
            return Book.builder()
                    .id(id)
                    .title(name)
                    .published(published)
                    .author(author)
                    .genre(genre)
                    .build();
        }

        private Author mapRowAuthor(ResultSet resultSet) throws SQLException {
            long id = resultSet.getLong("author_id");
            String name = resultSet.getString("authorName");
            LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
            return Author.builder()
                    .id(id)
                    .name(name)
                    .birthday(birthday)
                    .build();
        }

        private Genre mapRowGenre(ResultSet resultSet) throws SQLException {
            long id = resultSet.getLong("genre_id");
            String name = resultSet.getString("name");
            return Genre.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }
}
