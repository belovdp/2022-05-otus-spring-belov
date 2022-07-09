package ru.otus.spring.belov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.belov.domain.Genre;
import ru.otus.spring.belov.service.GenreService;

import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Shell команды по работе с жанрами
 */
@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    /** Сервис работы с жанрами */
    private final GenreService genreService;

    @ShellMethod(key = {"ig", "insGenre", "insertGenre"}, value = """
            Добавляет жанр.
                        Пример: insertGenre Фэнтези""")
    public String insert(@ShellOption(value = {"name"}, help = "Название жанра") String name) {
        var genre = genreService.save(name);
        return format("Жанр сохранён: %s", genre);
    }

    @ShellMethod(key = {"lg", "listGenre"}, value = "Выводит список жанров")
    public String showAll() {
        var genres = genreService.findAll();
        return format("Список жанров:\n\t%s", genres.stream()
                .map(Genre::toString)
                .collect(Collectors.joining("\n\t")));
    }

    @ShellMethod(key = {"fg", "findG", "findGenre"}, value = """
            Ищет жанр по названию
                        Пример: findGenre Роман""")
    public String findGenre(@ShellOption(value = {"name"}, help = "Название жанра") String name) {
        return genreService.findByName(name).map(Genre::toString).orElse("Жанр не найден");
    }

    @ShellMethod(key = {"fgFull", "findGFull", "findGenreFull"}, value = """
            Ищет жанр по названию и выводит все книги и комментарии к ним
                        Пример: findGenreFull Поэма""")
    public String findByNameWithBooksAndComments(@ShellOption(value = {"name"}, help = "Название жанра") String name) {
        return genreService.findByNameWithBooksAndComments(name).map(this::getGenreFullAsString).orElse("Жанр не найден");
    }

    private String getGenreFullAsString(Genre genre) {
        StringBuilder sb = new StringBuilder();
        sb.append("Жанр: ").append(genre.getName()).append("\n");
        sb.append("Книги:\n");
        genre.getBooks().forEach(book -> {
            sb.append("\t").append(book.getTitle()).append("\n");
            sb.append("\t\t").append("Комментарии:\n");
            book.getComments().forEach(comment ->
                    sb.append("\t\t\t").append(comment.getText()).append("\n")
            );
        });
        return sb.toString();
    }
}
