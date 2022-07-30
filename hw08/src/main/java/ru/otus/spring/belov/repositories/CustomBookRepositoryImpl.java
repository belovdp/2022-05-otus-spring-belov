package ru.otus.spring.belov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.BookComment;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class CustomBookRepositoryImpl implements CustomBookRepository {

    private final MongoTemplate template;

    @Override
    public BookComment saveComment(String text, String bookId) {
        Query query = new Query().addCriteria(Criteria.where("id").is(bookId));
        var bookComment = new BookComment(text);
        Update update = new Update().push("comments", bookComment);
        if (template.findAndModify(query, update, Book.class) == null) {
            throw new IllegalArgumentException(format("Не найдена книга с идентификатором %s", bookId));
        }
        return bookComment;
    }

    @Override
    public BookComment updateComment(String commentId, String text) {
        Query query = new Query().addCriteria(Criteria.where("comments.id").is(commentId));
        Update update = new Update().set("comments.$.text", text);
        var book = ofNullable(template.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Book.class))
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден комментарий с идентификатором %s", commentId)));
        return book.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(format("Не найден комментарий с идентификатором %s", commentId)));
    }

    @Override
    public void deleteCommentById(String commentId) {
        Update update = new Update().pull("comments", Query.query(Criteria.where("id").is(commentId)));
        template.findAndModify(new Query(), update, Book.class);
    }
}
