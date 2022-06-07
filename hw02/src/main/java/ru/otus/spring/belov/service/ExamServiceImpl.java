package ru.otus.spring.belov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.dao.QuestionDao;
import ru.otus.spring.belov.domain.Exam;

/**
 * Сервис проведения экзамена
 */
@Service
public class ExamServiceImpl implements ExamService {

    /** DAO по работе с вопросами тестирования */
    private final QuestionDao questionDao;
    /** Сервис по взаимодействию с пользователем */
    private final CommunicationService communicationService;
    /** Экзамен */
    private final Exam exam;

    /**
     * Конструктор
     * @param questionDao          DAO по работе с вопросами
     * @param communicationService сервис по взаимодействию с пользователем
     * @param requiredRightAnswers количество правильных ответов, требуемых для выполнения экзамена
     * @param shuffleAnswers       признак необходимости перемешивать вопросы
     * @param questionAttempts     количество попыток ответа на вопрос
     */
    public ExamServiceImpl(QuestionDao questionDao,
                           CommunicationService communicationService,
                           @Value("${examApp.requiredRightAnswers}") int requiredRightAnswers,
                           @Value("${examApp.shuffleAnswers}") boolean shuffleAnswers,
                           @Value("${examApp.questionAttempts}") int questionAttempts) {
        this.questionDao = questionDao;
        this.communicationService = communicationService;
        this.exam = new Exam(requiredRightAnswers, shuffleAnswers, questionAttempts);
    }

    @Override
    public void executeExam() {
        exam.setQuestions(questionDao.findAll());
        exam.setUser(communicationService.getUserInfo());
        exam.getQuestions().stream()
                .map(question -> {
                    communicationService.askQuestion(question, exam.isShuffleAnswers());
                    return communicationService.getAnswer(question, exam.getQuestionAttempts());
                })
                .forEach(exam::addAnswer);
        communicationService.printResult(exam);
    }
}
