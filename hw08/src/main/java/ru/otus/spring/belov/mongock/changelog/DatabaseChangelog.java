package ru.otus.spring.belov.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.domain.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "BelovDP", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "BelovDP")
    public void insertAuthors(MongockTemplate template) {
        var authors = new ArrayList<Author>();
        authors.add(Author.builder()
                .id("1")
                .name("Александр Сергеевич Пушкин")
                .birthday(LocalDate.parse("1799-05-26"))
                .build());
        authors.add(Author.builder()
                .id("2")
                .name("Джордж Реймонд Ричард Мартин")
                .birthday(LocalDate.parse("1948-09-20"))
                .build());
        template.insertAll(authors);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "BelovDP")
    public void insertGenres(MongockTemplate template) {
        var genres = new ArrayList<Genre>();
        genres.add(Genre.builder()
                .id("1")
                .name("Поэма")
                .build());
        genres.add(Genre.builder()
                .id("2")
                .name("Фэнтези")
                .build());
        genres.add(Genre.builder()
                .id("3")
                .name("Роман")
                .build());
        template.insertAll(genres);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "BelovDP")
    public void insertBooks(MongockTemplate template) {
        var books = new ArrayList<Book>();
        books.add(Book.builder()
                .id("1")
                .title("Руслан и Людмила")
                .published(LocalDate.parse("1820-01-01"))
                .genre(Genre.builder().id("1").build())
                .author(Author.builder().id("1").build())
                .build());
        books.add(Book.builder()
                .id("2")
                .title("Медный всадник")
                .published(LocalDate.parse("1833-01-01"))
                .genre(Genre.builder().id("1").build())
                .author(Author.builder().id("1").build())
                .build());
        books.add(Book.builder()
                .id("3")
                .title("Игра престолов")
                .published(LocalDate.parse("1996-08-06"))
                .genre(Genre.builder().id("2").build())
                .author(Author.builder().id("2").build())
                .build());
        books.add(Book.builder()
                .id("4")
                .title("Битва королей")
                .published(LocalDate.parse("1998-11-16"))
                .genre(Genre.builder().id("2").build())
                .author(Author.builder().id("2").build())
                .build());
        books.add(Book.builder()
                .id("5")
                .title("Умирающий Свет")
                .published(LocalDate.parse("1977-10-05"))
                .genre(Genre.builder().id("3").build())
                .author(Author.builder().id("2").build())
                .build());
        template.insertAll(books);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "BelovDP")
    public void insertComments(MongockTemplate template) {
        var book = template.findById("1", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new BookComment("Какой то комментарий"),
                new BookComment("Какой то комментарий 2"),
                new BookComment("Ещё какой то комментарий"),
                new BookComment("А вот ещё")
        ));
        template.save(book);

        book = template.findById("2", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new BookComment("Трололо"),
                new BookComment("Пыщ пыщ")
        ));
        template.save(book);

        book = template.findById("3", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new BookComment("Устал придумывать")
        ));
        template.save(book);

        book = template.findById("4", Book.class);
        Objects.requireNonNull(book).setComments(Set.of(
                new BookComment("Всё")
        ));
        template.save(book);
    }
}
