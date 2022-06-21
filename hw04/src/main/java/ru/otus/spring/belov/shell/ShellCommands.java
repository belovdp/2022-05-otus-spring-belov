package ru.otus.spring.belov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.belov.component.LocaleHolder;
import ru.otus.spring.belov.config.ExamAppProperties;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.User;
import ru.otus.spring.belov.service.ExamService;
import ru.otus.spring.belov.service.MessageService;

import java.util.Locale;

/**
 * Shell команды приложения
 */
@RequiredArgsConstructor
@ShellComponent
public class ShellCommands {

    /** Компонент по работе с локалью */
    private final LocaleHolder localeHolder;
    /** Сервис по работе с сообщениями */
    private final MessageService messageService;
    /** Сервис по работе с тестирование */
    private final ExamService examService;
    /** Настройки приложения */
    private final ExamAppProperties examAppProperties;
    /** Экзаменуемый пользователь */
    private User user;

    /**
     * Изменяет язык используемый при тестировании
     * @param locale язык
     * @return сообщение о результате изменения языка
     */
    @ShellMethod(value = "Change language", key = {"lang", "language"})
    public String changeLanguage(@ShellOption(defaultValue = "en") Locale locale) {
        try {
            localeHolder.changeLocale(locale);
            return messageService.getMessage("exam.shell.language_changed");
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }
    }

    /**
     * Устанавливаем экзаменуемого пользователя
     * @param firstName имя пользователя
     * @param lastName  фамилия пользователя
     */
    @ShellMethod(value = "Set User info command", key = {"u", "user"})
    public void user(@ShellOption String firstName, @ShellOption String lastName) {
        this.user = new User(firstName, lastName);
    }

    /**
     * Запускает экзамен. Экзамен нельзя запустить без экзаменуемого пользователя
     */
    @ShellMethod(value = "start", key = {"s", "start"})
    @ShellMethodAvailability(value = "isExamAvailable")
    public void startExam() {
        var exam = new Exam(examAppProperties.getRequiredRightAnswers(), examAppProperties.isShuffleAnswers(), examAppProperties.getQuestionAttempts(), user);
        examService.executeExam(exam);
    }

    /**
     * Возвращает информацию о доступности экзамена
     * @return информация о доступности экзамена
     */
    private Availability isExamAvailable() {
        return user == null ? Availability.unavailable("Сначала введите Фамилию и Имя тестируемого") : Availability.available();
    }
}
