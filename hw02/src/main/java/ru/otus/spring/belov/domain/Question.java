package ru.otus.spring.belov.domain;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Вопрос для тестирования
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    /** Вопрос */
    @CsvBindByName(required = true)
    private String question;
    /** Варианты ответов */
    @CsvBindAndSplitByName(elementType = String.class, collectionType = ArrayList.class, splitOn = "\\,")
    private List<String> answers;
    /** Индекс правильного ответа */
    @CsvBindByName(required = true)
    private int rightAnswerIndex;

    /**
     * Перемешивает ответы на вопросы
     */
    public void shuffleAnswers() {
        String rightAnswer = answers.get(rightAnswerIndex);
        Collections.shuffle(answers);
        rightAnswerIndex = answers.indexOf(rightAnswer);
    }
}
