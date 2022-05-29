package ru.otus.spring.belov.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Информация по экзамену
 */
@Setter
@Getter
@RequiredArgsConstructor
public class Exam {
    /** Вопросы */
    private final List<Question> questions;
    /** Количество правильных ответов, требуемых для выполнения экзамена */
    private final int requiredRightAnswers;
    /** Признак необходимости перемешивать вопросы */
    private final boolean shuffleAnswers;
    /** Количество попыток ответа на вопрос */
    private final int questionAttempts;
     /** Пользователь */
    private User user;
    /** Ответы */
    private final List<Answer> answers = new ArrayList<>();

    /**
     * Добавляет ответ пользователя
     * @param answer ответ пользователя
     */
    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    /**
     * Возвращает количество правильных ответов
     * @return количество правильных ответов
     */
    public long getRightAnswersCount() {
        return answers.stream()
                .filter(Answer::isRightAnswer)
                .count();
    }

    /**
     * Возвращает результат выполнения экзамена (Пройден/не пройден)
     * @return результат выполнения экзамена
     */
    public boolean isExamPassed() {
        return requiredRightAnswers <= getRightAnswersCount();
    }
}