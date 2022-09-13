package ru.otus.spring.belov.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.belov.domain.document.AuthorDocument;
import ru.otus.spring.belov.domain.document.BookCommentDocument;
import ru.otus.spring.belov.domain.document.BookDocument;
import ru.otus.spring.belov.domain.document.GenreDocument;
import ru.otus.spring.belov.domain.relation.Author;
import ru.otus.spring.belov.domain.relation.Book;
import ru.otus.spring.belov.domain.relation.BookComment;
import ru.otus.spring.belov.domain.relation.Genre;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Конвертер сущностей разных БД
 */
@Mapper(componentModel = "spring")
public abstract class EntityMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Mapping(target = "oldId", source = "id")
    @Mapping(target = "id", ignore = true)
    public abstract AuthorDocument toAuthorDocument(Author author);

    @Mapping(target = "oldId", source = "id")
    @Mapping(target = "id", ignore = true)
    public abstract GenreDocument toGenreDocument(Genre genre);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genre", qualifiedByName = "findGenreByOldId")
    @Mapping(target = "author", qualifiedByName = "findAuthorByOldId")
    public abstract BookDocument toBookDocument(Book book);

    @Mapping(target = "id", ignore = true)
    public abstract BookCommentDocument toBookComment(BookComment bookComment);

    @Named("findGenreByOldId")
    protected GenreDocument findGenreByOldId(Genre genre) {
        return mongoTemplate.findOne(query(where("oldId").is(genre.getId())), GenreDocument.class);
    }

    @Named("findAuthorByOldId")
    protected AuthorDocument findAuthorByOldId(Author genre) {
        return mongoTemplate.findOne(query(where("oldId").is(genre.getId())), AuthorDocument.class);
    }
}
