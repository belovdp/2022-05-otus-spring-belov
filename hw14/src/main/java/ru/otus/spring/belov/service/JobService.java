package ru.otus.spring.belov.service;

/**
 * Сервис по работе с задачей
 */
public interface JobService {

    /**
     * Запускает задачу
     * @throws Exception ошибка
     */
    void run() throws Exception;

    /**
     * Перезапускает задачу
     * @throws Exception ошибка
     */
    void restart() throws Exception;

    /**
     * Отображает информация по запущенной задаче
     * @throws Exception ошибка
     */
    String showJobInfo() throws Exception;

    /**
     * Возвращает признак того что задача была уже запущена
     * @return признак того что задача была уже запущена
     */
    boolean isAlreadyRun();
}
