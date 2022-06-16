package ru.otus.spring.belov.service;

import ru.otus.spring.belov.domain.Answer;
import ru.otus.spring.belov.domain.Exam;
import ru.otus.spring.belov.domain.Question;
import ru.otus.spring.belov.domain.User;

import java.util.Locale;

/**
 * Кронтракт на взаимодействие с пользователем
 */
public interface CommunicationService {

    /**
     * Возвращает информацию о пользователе
     * @return информация о пользователе
     */
    User getUserInfo();

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

    /**
     * Возвращает выбранную пользователем локаль
     * @return локаль
     */
    Locale chooseLocale();
}
