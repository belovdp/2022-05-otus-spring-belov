package ru.otus.spring.belov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.belov.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO по работе с авторами через JDBC
 */
@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    /** Запрос на вставку нового автора */
    private static final String INSERT_AUTHOR = "INSERT INTO authors (name, birthday) VALUES (:name, :birthday)";
    /** Запрос на получение всех авторов */
    private static final String SELECT_AUTHORS = "SELECT id, name, birthday FROM authors";
    /** Запрос на поиск автора по частичному совпадению в ФИО */
    private static final String SELECT_AUTHOR_BY_NAME_CONTAINING = "SELECT id, name, birthday FROM authors WHERE name LIKE '%' || :name || '%'";
    /** Запрос на поиск автора по идентификатору */
    private static final String SELECT_AUTHOR_BY_ID = "SELECT id, name, birthday FROM authors WHERE id  = :id";
    /** Маппер автора */
    private final AuthorMapper authorMapper = new AuthorMapper();
    /** Компонент для работы с jdbc */
    private final NamedParameterJdbcOperations jdbc;


    @Override
    public Author save(Author author) {
        var keyHolder = new GeneratedKeyHolder();
        var params = Map.of(
                "name", author.getName(),
                "birthday", author.getBirthday()
        );
        jdbc.update(INSERT_AUTHOR, new MapSqlParameterSource(params), keyHolder);
        author.setId(keyHolder.getKeyAs(Long.class));
        return author;
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query(SELECT_AUTHORS, authorMapper);
    }

    @Override
    public List<Author> findByNameContaining(String name) {
        return jdbc.query(SELECT_AUTHOR_BY_NAME_CONTAINING, Map.of("name", name), authorMapper);
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(jdbc.query(SELECT_AUTHOR_BY_ID, Map.of("id", id), rs -> rs.next() ? authorMapper.mapRow(rs, 1) : null));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
            return Author.builder()
                    .id(id)
                    .name(name)
                    .birthday(birthday)
                    .build();
        }
    }
}
