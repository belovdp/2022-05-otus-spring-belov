package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.belov.dao.QuestionDao;
import ru.otus.spring.belov.domain.Question;

import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Сервис проведения экзамена
 */
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    /** DAO по работе с вопросами тестирования */
    private final QuestionDao questionDao;

    @Override
    public void printQuestions() {
        List<Question> questions = questionDao.findAll();
        validateQuestions(questions);
        questions.forEach(question -> {
            System.out.println("Question: " +question.getQuestion());
            System.out.println("Answers:");
            for (int answerIndex = 0; answerIndex < question.getAnswers().size(); answerIndex++) {
                String answer = question.getAnswers().get(answerIndex);
                System.out.println(answerIndex + " - " + answer);
            }
            System.out.println();
        });
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
