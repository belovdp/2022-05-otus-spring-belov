package ru.otus.spring.belov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.belov.domain.Book;
import ru.otus.spring.belov.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Shell команды по работе с авторами
 */
@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    /** Сервис работы с книгами */
    private final BookService bookService;

    @ShellMethod(key = {"ib", "insBook", "insertBook"}, value = """
            Добавляет книгу.
                        Пример: insertBook 'Война и мир' 1799-05-26 1 1""")
    public String insert(@ShellOption(value = {"title"}, help = "Название книги") String title,
                         @ShellOption(value = {"published"}, help = "Дата публикации книги") String published,
                         @ShellOption(value = {"genreId"}, help = "Идентификатор жанра") long genreId,
                         @ShellOption(value = {"authorId"}, help = "Идентификатор автора") long authorId) {
        var book = bookService.save(title, published, genreId, authorId);
        return format("Книга сохранёна: %s", book);
    }

    @ShellMethod(key = {"ub", "updBook", "updateBook"}, value = """
            Изменяет книгу.
                        Пример: updateBook 1 'Война и мир' 1799-05-26 1 1""")
    public String update(@ShellOption(value = {"id"}, help = "Идентификатор модифицируемой книги") long id,
                         @ShellOption(value = {"title"}, help = "Название книги") String title,
                         @ShellOption(value = {"published"}, help = "Дата публикации книги") String published,
                         @ShellOption(value = {"genreId"}, help = "Идентификатор жанра") long genreId,
                         @ShellOption(value = {"authorId"}, help = "Идентификатор автора") long authorId) {
        var book = bookService.update(id, title, published, genreId, authorId);
        return format("Книга изменена: %s", book);
    }

    @ShellMethod(key = {"db", "delBook", "deleteBook"}, value = """
            Удаляет книгу.
                        Пример: deleteBook 1""")
    public String delete(@ShellOption(value = {"id"}, help = "Идентификатор удаляемой книги") long id) {
        bookService.deleteById(id);
        return format("Книга с идентификатором %s удалена", id);
    }

    @ShellMethod(key = {"fb", "findBook"}, value = """
            Ищет книгу по идентификатору.
                        Пример: findBook 1""")
    public String show(@ShellOption(value = {"id"}, help = "Идентификатор искомой книги") long id) {
        var book = bookService.findById(id);
        return getBookWithCommentsAsString(book);
    }

    @ShellMethod(key = {"lb", "listBooks"}, value = "Выводит список книг")
    public String showAll() {
        var books = bookService.findAll();
        return getBookAsString(books);
    }

    @ShellMethod(key = {"findByGenre"}, value = """
            Ищет книги по жанру.
                        Пример: findByGenre Роман""")
    public String showAllByGenreName(@ShellOption(value = {"genre"}, help = "Название жанра как в справочнике") String genreName) {
        var books = bookService.findAllByGenreName(genreName);
        return getBookAsString(books);
    }

    private String getBookAsString(List<Book> books) {
        return format("Список книг:\n\t%s", books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n\t"))
        );
    }

    private String getBookWithCommentsAsString(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("Книга:\n").append(book);
        book.getComments().forEach(comment ->
            sb.append("\t").append(comment).append("\n")
        );
        return sb.toString();
    }
}
