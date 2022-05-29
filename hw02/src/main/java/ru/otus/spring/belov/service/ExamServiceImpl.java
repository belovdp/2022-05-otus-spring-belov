package ru.otus.spring.belov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.dao.QuestionDao;
import ru.otus.spring.belov.domain.Answer;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.Question;
import ru.otus.spring.belov.domain.User;

import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис проведения экзамена
 */
@Service
public class ExamServiceImpl implements ExamService {

    /** Сервис взаимодействия с пользователем */
    private final UserCommunicationService userCommunicationService;
    /** Экзамен */
    private final Exam exam;

    /**
     * Конструктор
     * @param questionDao              DAO по работе с вопросами
     * @param userCommunicationService сервис взаимодействия с пользователем
     * @param requiredRightAnswers     количество правильных ответов, требуемых для выполнения экзамена
     * @param shuffleAnswers           признак необходимости перемешивать вопросы
     * @param questionAttempts         количество попыток ответа на вопрос
     */
    public ExamServiceImpl(QuestionDao questionDao,
                           UserCommunicationService userCommunicationService,
                           @Value("${examApp.requiredRightAnswers}") int requiredRightAnswers,
                           @Value("${examApp.shuffleAnswers}") boolean shuffleAnswers,
                           @Value("${examApp.questionAttempts}") int questionAttempts) {
        this.userCommunicationService = userCommunicationService;
        List<Question> questions = questionDao.findAll();
        validateQuestions(questions);
        this.exam = new Exam(questions, requiredRightAnswers, shuffleAnswers, questionAttempts);
    }

    @Override
    public void executeExam() {
        exam.setUser(getUserInfo());
        exam.getQuestions().stream()
                .map(this::askQuestion)
                .forEach(exam::addAnswer);
        printResult(exam);
    }

    /**
     * Возвращает информацию о пользователе
     * @return информация о пользователе
     */
    private User getUserInfo() {
        userCommunicationService.print("Enter your name");
        String firstName = userCommunicationService.readAnswer();
        userCommunicationService.print("Enter your last name");
        String lastName = userCommunicationService.readAnswer();
        return new User(firstName, lastName);
    }

    /**
     * Возвращает ответ пользователя на вопрос
     * Предварительно этот вопрос задает пользователю
     * @param question вопрос
     * @return ответ пользователя на вопрос
     */
    private Answer askQuestion(Question question) {
        userCommunicationService.print("Question: " + question.getQuestion());
        userCommunicationService.print("Answers:");
        if (exam.isShuffleAnswers()) {
            question.shuffleAnswers();
        }
        for (int answerIndex = 0; answerIndex < question.getAnswers().size(); answerIndex++) {
            String answer = question.getAnswers().get(answerIndex);
            userCommunicationService.print(format("{0} - {1}", answerIndex, answer));
        }
        Answer answer = getAnswer(question, 1);
        userCommunicationService.print("");
        return answer;
    }

    /**
     * Возвращает ответ пользователя
     * У пользователя будет несколько попыток ввести правильный ответ, согласно настройкам экзамена
     * @param question вопрос
     * @param attempt  номер попытки ответа
     * @return ответ пользователя
     */
    private Answer getAnswer(Question question, int attempt) {
        int answer = readAnswer(question);
        if (question.getRightAnswerIndex() != answer && attempt < exam.getQuestionAttempts()) {
            userCommunicationService.printError(format("Wrong. Remaining attempts: {0}. Try again...", exam.getQuestionAttempts() - attempt));
            return getAnswer(question, ++attempt);
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
        try {
            userCommunicationService.print("Your answer:");
            int answer = Integer.parseInt(userCommunicationService.readAnswer());
            int answersCount = question.getAnswers().size();
            if (answersCount - 1 < answer || answer < 0) {
                userCommunicationService.printError(format("The answer should be in the range from 0 to {0}. Try again", answersCount - 1));
                return readAnswer(question);
            }
            return answer;
        } catch (NumberFormatException e) {
            userCommunicationService.printError("You must specify a number. Try again...");
            return readAnswer(question);
        }
    }

    /**
     * Выводит результат тестирования
     * @param exam экзамен
     */
    private void printResult(Exam exam) {
        userCommunicationService.print(format("User: {0} {1}", exam.getUser().getFirstName(), exam.getUser().getLastName()));
        userCommunicationService.print(format("Right answers: {0}/{1}", exam.getRightAnswersCount(), exam.getQuestions().size()));
        if (exam.isExamPassed()) {
            userCommunicationService.print("PASSED");
        } else {
            userCommunicationService.printError("NOT PASSED");
        }
    }

    /**
     * Проверяет валидность вопросов
     * @param questions список вопросов
     * @throws IllegalStateException если на вопрос меньше двух возможных ответов или невозможно определить правильный ответ
     */
    private void validateQuestions(List<Question> questions) {
        questions.forEach(question -> {
            if (question.getAnswers() == null || question.getAnswers().size() < 2) {
                throw new IllegalStateException(format("У вопроса \"{0}\" должно быть несколько вариантов ответа", question.getQuestion()));
            }
            if (question.getAnswers().size() - 1 < question.getRightAnswerIndex()) {
                throw new IllegalStateException(format("У вопроса \"{0}\" указан неверный ответ", question.getQuestion()));
            }
        });
    }
}
