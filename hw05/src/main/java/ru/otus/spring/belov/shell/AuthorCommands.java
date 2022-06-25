package ru.otus.spring.belov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.belov.domain.Author;
import ru.otus.spring.belov.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Shell команды по работе с авторами
 */
@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    /** Сервис работы с авторами */
    private final AuthorService authorService;

    /**
     * Команда на сохранение автора
     * @param name     имя автора
     * @param birthDay день рождения
     * @return информация о сохранении
     */
    @ShellMethod(key = {"ia", "insAuthor", "insertAuthor"}, value = """
            Добавляет автора.
                        Пример: insertAuthor 'Пушкин А.С.' 1799-05-26""")
    public String insert(@ShellOption({"name"}) String name, @ShellOption({"birthDay"}) String birthDay) {
        var author = authorService.save(name, birthDay);
        return format("Автор сохранён: %s", author);
    }

    /**
     * Отображает список всех авторов
     * @return список всех авторов
     */
    @ShellMethod(key = {"la", "listAuthors"}, value = "Выводит список авторов")
    public String showAll() {
        var authors = authorService.findAll();
        return getAuthorsAsString(authors);
    }

    /**
     * Поиск автора по частичному совпадению в ФИО
     * @param name искомое имя
     * @return найденные авторы
     */
    @ShellMethod(key = {"fa", "findA", "findAuthor"}, value = """
            Ищет автора, содержащего в ФИО искомую строку
                        Пример: findAuthor Пушк""")
    public String findAuthor(@ShellOption({"name"}) String name) {
        var authors = authorService.findByNameContaining(name);
        return getAuthorsAsString(authors);
    }

    private String getAuthorsAsString(List<Author> authors) {
        return format("Список авторов:\n%s", authors.stream()
                .map(Author::toString)
                .collect(Collectors.joining("\n"))
        );
    }
}
