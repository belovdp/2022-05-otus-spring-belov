package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.domain.Answer;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.Question;
import ru.otus.spring.belov.domain.User;

import static java.text.MessageFormat.format;

/**
 * Класс для взаимодействие с пользователем через консоль
 */
@Service
@RequiredArgsConstructor
public class ConsoleCommunicationService implements CommunicationService {

    /** Сервис ввода вывода информации */
    private final IOService ioService;

    @Override
    public User getUserInfo() {
        ioService.print("Enter your name");
        var firstName = ioService.readAnswer();
        ioService.print("Enter your last name");
        var lastName = ioService.readAnswer();
        return new User(firstName, lastName);
    }

    @Override
    public void askQuestion(Question question, boolean shuffle) {
        ioService.printDelimiter();
        ioService.print("Question: " + question.getQuestion());
        ioService.print("Answers:");
        if (shuffle) {
            question.shuffleAnswers();
        }
        for (var answerIndex = 0; answerIndex < question.getAnswers().size(); answerIndex++) {
            String answer = question.getAnswers().get(answerIndex);
            ioService.print(format("{0} - {1}", answerIndex, answer));
        }
    }

    @Override
    public Answer getAnswer(Question question, int attempts) {
        var answer = readAnswer(question);
        if (question.getRightAnswerIndex() != answer && 1 < attempts) {
            attempts--;
            ioService.printError(format("Wrong. Remaining attempts: {0}. Try again...", attempts));
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
        ioService.print("Your answer:");
        var answer = ioService.readIntAnswer();
        var answersCount = question.getAnswers().size();
        if (answersCount - 1 < answer || answer < 0) {
            ioService.printError(format("The answer should be in the range from 0 to {0}. Try again", answersCount - 1));
            return readAnswer(question);
        }
        return answer;
    }

    @Override
    public void printResult(Exam exam) {
        ioService.printDelimiter();
        ioService.print(format("User: {0} {1}", exam.getUser().getFirstName(), exam.getUser().getLastName()));
        ioService.print(format("Right answers: {0}/{1}", exam.getRightAnswersCount(), exam.getQuestions().size()));
        if (exam.isExamPassed()) {
            ioService.print("PASSED");
        } else {
            ioService.printError("NOT PASSED");
        }
    }
}
