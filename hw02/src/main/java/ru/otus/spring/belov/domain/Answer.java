package ru.otus.spring.belov.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Ответ на вопрос
 */
@Getter
@RequiredArgsConstructor
public class Answer {
    /** Вопрос на который отвечали */
    private final Question question;
    /** Ответ */
    private final int answer;

    /**
     * Возвращает признак правильности ответа
     * @return признак правильности ответа
     */
    public boolean isRightAnswer() {
        return question.getRightAnswerIndex() == answer;
    }
}
