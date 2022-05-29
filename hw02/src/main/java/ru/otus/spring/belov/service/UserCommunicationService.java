package ru.otus.spring.belov.service;

/**
 * Кронтракт на взаимодействие с пользователем
 */
public interface UserCommunicationService {

    /**
     * Выводит сообщение пользователю
     * @param message сообщение
     */
    void print(String message);

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
}
