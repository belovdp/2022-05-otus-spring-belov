package ru.otus.homework.service;

import ru.otus.homework.domain.Human;

/**
 * Сервис для частичной мобилизации
 */
public interface PartialMobilizationService {

    /**
     * Тренировка человека.
     * Не смотрим на то что человек уже умеет, а учим тому что надо. Был танкист? пофиг, сделаем сапёра
     * @param human человек
     * @return человек
     * @throws InterruptedException ошибка
     */
    Human training(Human human) throws InterruptedException;

    /**
     * Запускает мобилизацию
     * @throws InterruptedException ошибка
     */
    void startMobilization() throws InterruptedException;
}
