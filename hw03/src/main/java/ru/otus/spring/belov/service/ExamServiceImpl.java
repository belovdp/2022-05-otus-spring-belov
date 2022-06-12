package ru.otus.spring.belov.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.belov.config.ExamAppProperties;
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
    /** Настройки экзамена */
    private final ExamAppProperties examAppProperties;

    /**
     * Конструктор
     * @param questionDao          DAO по работе с вопросами
     * @param communicationService сервис по взаимодействию с пользователем
     * @param examAppProperties    настройки экзамена
     */
    public ExamServiceImpl(QuestionDao questionDao,
                           CommunicationService communicationService,
                           ExamAppProperties examAppProperties) {
        this.questionDao = questionDao;
        this.communicationService = communicationService;
        this.examAppProperties = examAppProperties;
    }

    @Override
    public Exam executeExam() {
        var exam = new Exam(examAppProperties.getRequiredRightAnswers(), examAppProperties.isShuffleAnswers(), examAppProperties.getQuestionAttempts());
        exam.setQuestions(questionDao.findAll());
        exam.setUser(communicationService.getUserInfo());
        exam.getQuestions().stream()
                .map(question -> {
                    communicationService.askQuestion(question, exam.isShuffleAnswers());
                    return communicationService.getAnswer(question, exam.getQuestionAttempts());
                })
                .forEach(exam::addAnswer);
        communicationService.printResult(exam);
        return exam;
    }
}
