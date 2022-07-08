package ru.otus.spring.belov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.belov.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO по работе с жанрами через JDBC
 */
@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    /** Запрос на вставку нового жанра */
    private static final String INSERT_GENRE = "INSERT INTO genres (name) VALUES (:name)";
    /** Запрос на получение всех жанров */
    private static final String SELECT_GENRES = "SELECT id, name FROM genres";
    /** Запрос на поиск жанра по названию */
    private static final String SELECT_GENRE_BY_NAME = "SELECT id, name FROM genres WHERE name = :name";
    /** Запрос на поиск жанра по идентификатору */
    private static final String SELECT_GENRE_BY_ID = "SELECT id, name FROM genres WHERE id = :id";
    /** Маппер жанра */
    private final GenreMapper genreMapper = new GenreMapper();
    /** Компонент для работы с jdbc */
    private final NamedParameterJdbcOperations jdbc;


    @Override
    public Genre save(Genre genre) {
        var keyHolder = new GeneratedKeyHolder();
        var params = Map.of("name", genre.getName());
        jdbc.update(INSERT_GENRE, new MapSqlParameterSource(params), keyHolder);
        genre.setId(keyHolder.getKeyAs(Long.class));
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query(SELECT_GENRES, genreMapper);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return Optional.ofNullable(jdbc.query(SELECT_GENRE_BY_NAME, Map.of("name", name), rs -> rs.next() ? genreMapper.mapRow(rs, 1) : null));
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(jdbc.query(SELECT_GENRE_BY_ID, Map.of("id", id), rs -> rs.next() ? genreMapper.mapRow(rs, 1) : null));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return Genre.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }
}
