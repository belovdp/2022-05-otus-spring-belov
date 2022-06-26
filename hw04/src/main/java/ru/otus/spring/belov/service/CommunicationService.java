package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Answer;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.Question;

/**
 * Кронтракт на взаимодействие с пользователем
 */
public interface CommunicationService {

    /**
     * Задает вопрос пользователю
     * Предварительно этот вопрос задает пользователю
     * @param question вопрос
     * @param shuffle  {@code true} - ответы надо перемешать, иначе {@code false}
     */
    void askQuestion(Question question, boolean shuffle);

    /**
     * Возвращает ответ пользователя
     * У пользователя будет несколько попыток ввести правильный ответ, согласно настройкам экзамена
     * @param question вопрос
     * @return ответ пользователя
     */
    Answer getAnswer(Question question, int attempts);

    /**
     * Выводит результат тестирования
     * @param exam экзамен
     */
    void printResult(Exam exam);
}
