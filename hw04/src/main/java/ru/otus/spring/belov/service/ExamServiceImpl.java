package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.component.LocaleHolder;
import ru.otus.spring.belov.config.ExamAppProperties;
import ru.otus.spring.belov.dao.QuestionDao;
import ru.otus.spring.belov.domain.Exam;

/**
 * Сервис проведения экзамена
 */
@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService {

    /** DAO по работе с вопросами тестирования */
    private final QuestionDao questionDao;
    /** Сервис по взаимодействию с пользователем */
    private final CommunicationService communicationService;
    /** Настройки экзамена */
    private final ExamAppProperties examAppProperties;
    /** Компонент по работе с локалью */
    private final LocaleHolder localeHolder;

    @Override
    public Exam executeExam() {
        var locale = communicationService.chooseLocale();
        localeHolder.changeLocale(locale);
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
