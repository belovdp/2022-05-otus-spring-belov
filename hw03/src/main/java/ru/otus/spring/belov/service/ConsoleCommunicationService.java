package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.config.ExamAppProperties;
import ru.otus.spring.belov.domain.Answer;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.Question;
import ru.otus.spring.belov.domain.User;

import java.util.Locale;

/**
 * Класс для взаимодействие с пользователем через консоль
 */
@Service
@RequiredArgsConstructor
public class ConsoleCommunicationService implements CommunicationService {

    /** Сервис ввода вывода информации */
    private final IOService ioService;
    /** Компонент локализаци */
    private final MessageService messageService;
    /** Настройки приложения */
    private final ExamAppProperties examAppProperties;

    @Override
    public Locale chooseLocale() {
        ioService.print(messageService.getMessage("locale.choose"));
        for (var answerIndex = 0; answerIndex < examAppProperties.getLocales().size(); answerIndex++) {
            ioService.print(messageService.getMessage("exam.chooser_pattern", answerIndex, examAppProperties.getLocales().get(answerIndex)));
        }
        return examAppProperties.getLocales().get(ioService.readIntAnswer());
    }

    @Override
    public User getUserInfo() {
        ioService.print(messageService.getMessage("exam.user.enter_name"));
        var firstName = ioService.readAnswer();
        ioService.print(messageService.getMessage("exam.user.enter_last_name"));
        var lastName = ioService.readAnswer();
        return new User(firstName, lastName);
    }

    @Override
    public void askQuestion(Question question, boolean shuffle) {
        ioService.printDelimiter();
        ioService.print(messageService.getMessage("exam.question") + question.getQuestion());
        ioService.print(messageService.getMessage("exam.question.answers"));
        if (shuffle) {
            question.shuffleAnswers();
        }
        for (var answerIndex = 0; answerIndex < question.getAnswers().size(); answerIndex++) {
            var answer = question.getAnswers().get(answerIndex);
            ioService.print(messageService.getMessage("exam.chooser_pattern", answerIndex, answer));
        }
    }

    @Override
    public Answer getAnswer(Question question, int attempts) {
        var answer = readAnswer(question);
        if (question.getRightAnswerIndex() != answer && 1 < attempts) {
            attempts--;
            ioService.printError(messageService.getMessage("exam.answer.try_again", attempts));
            return getAnswer(question, attempts);
        }
        return new Answer(question, answer);
    }

    /**
     * Возвращает ответ пользователя в виде цифры.
     * Если пользователь дурак и ввёл некорректную цифру,
     * заставляем его повторять процедуру, пока не поумнеет
     * @param question вопрос
     * @return ответ пользователя в виде цифры
     */
    private int readAnswer(Question question) {
        ioService.print(messageService.getMessage("exam.answer.write"));
        var answer = ioService.readIntAnswer();
        var answersCount = question.getAnswers().size();
        if (answersCount - 1 < answer || answer < 0) {
            ioService.printError(messageService.getMessage("exam.answer.incorrect", answersCount - 1));
            return readAnswer(question);
        }
        return answer;
    }

    @Override
    public void printResult(Exam exam) {
        ioService.printDelimiter();
        ioService.print(messageService.getMessage("exam.result.user", exam.getUser().getFirstName(), exam.getUser().getLastName()));
        ioService.print(messageService.getMessage("exam.result.answers", exam.getRightAnswersCount(), exam.getQuestions().size()));
        if (exam.isExamPassed()) {
            ioService.print(messageService.getMessage("exam.result.passed"));
        } else {
            ioService.printError(messageService.getMessage("exam.result.not_passed"));
        }
    }
}
