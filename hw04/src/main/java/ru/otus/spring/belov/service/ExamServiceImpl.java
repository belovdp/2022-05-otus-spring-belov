package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    @Override
    public void executeExam(Exam exam) {
        exam.setQuestions(questionDao.findAll());
        exam.getQuestions().stream()
                .map(question -> {
                    communicationService.askQuestion(question, exam.isShuffleAnswers());
                    return communicationService.getAnswer(question, exam.getQuestionAttempts());
                })
                .forEach(exam::addAnswer);
        communicationService.printResult(exam);
    }
}
