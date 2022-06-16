package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Сервис по работе с сообщениями
 */
@RequiredArgsConstructor
@Component
public class MessageService {

    /** Источник сообщений */
    private final MessageSource messageSource;
    /** Текущая локаль */
    private Locale locale = Locale.getDefault();

    /**
     * Изменить локаль
     * @param locale локаль
     */
    public void changeLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Возвращает текст сообщений
     * @param key  ключ сообщений
     * @param args аргументы для подстановки в сообщение
     * @return текст сообщений
     */
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }
}
