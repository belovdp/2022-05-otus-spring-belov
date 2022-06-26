package ru.otus.spring.belov.service;

/**
 * Сервис ввода вывода информации
 */
public interface IOService {

    /**
     * Выводит сообщение пользователю
     * @param message сообщение
     */
    void print(String message);

    /**
     * Выводит разделитель
     */
    void printDelimiter();

    /**
     * Выводит сообщение с ошибкой пользователю
     * @param message сообщение
     */
    void printError(String message);

    /**
     * Считывает ответ пользователя
     * @return ответ пользователя
     */
    String readAnswer();

    /**
     * Считывает числовой ответ пользователя
     * @return числовой ответ пользователя
     */
    int readIntAnswer();
}
