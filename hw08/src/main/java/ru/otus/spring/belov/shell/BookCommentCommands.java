package ru.otus.spring.belov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.belov.domain.BookComment;
import ru.otus.spring.belov.service.BookCommentService;

import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Shell команды по работе с жанрами
 */
@ShellComponent
@RequiredArgsConstructor
public class BookCommentCommands {

    /** Сервис работы с комментариями */
    private final BookCommentService bookCommentService;

    @ShellMethod(key = {"ibc", "insbc", "insertBookComment"}, value = """
            Добавляет комментарий для книги.
                        Пример: insertBookComment 'Замечательная книга' 1""")
    public String insert(@ShellOption(value = {"text"}, help = "Текст комментария") String text,
                         @ShellOption(value = {"bookId"}, help = "Идентификатор книги") String bookId) {
        var bookComment = bookCommentService.save(text, bookId);
        return format("Комментарий сохранён: %s", bookComment);
    }

    @ShellMethod(key = {"ubc", "updBookComment", "updateBookComment"}, value = """
            Изменяет комментарий.
                        Пример: updateBookComment 1 'Не замечательная книга'""")
    public String update(@ShellOption(value = {"id"}, help = "Идентификатор модифицируемого комментария") String id,
                         @ShellOption(value = {"text"}, help = "Текст комментария") String text) {
        var bookComment = bookCommentService.update(id, text);
        return format("Комментарий изменен: %s", bookComment);
    }

    @ShellMethod(key = {"fbcByBookId", "findBookCommentsByBookId"}, value = """
            Ищет комментарий по идентификатору.
                        Пример: findBookCommentsByBookId 1""")
    public String findBookCommentsByBookId(@ShellOption(value = {"id"}, help = "Идентификатор книги") String id) {
        var bookComments = bookCommentService.findBookCommentsByBookId(id);
        return format("Список комментариев:\n\t%s", bookComments.stream()
                .map(BookComment::toString)
                .collect(Collectors.joining("\n\t")));
    }

    @ShellMethod(key = {"dbc", "delBookComment", "deleteBookComment"}, value = """
            Удаляет комментарий.
                        Пример: deleteBookComment 1""")
    public String delete(@ShellOption(value = {"id"}, help = "Идентификатор удаляемого комментария") String id) {
        bookCommentService.deleteById(id);
        return format("Комментарий с идентификатором %s удален", id);
    }
}
