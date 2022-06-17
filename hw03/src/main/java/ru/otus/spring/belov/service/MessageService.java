package ru.otus.spring.belov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.belov.config.ExamAppProperties;

import java.util.Locale;

/**
 * Сервис по работе с сообщениями
 */
@Component
public class MessageService {

    /** Источник сообщений */
    private final MessageSource messageSource;
    /** Текущая локаль */
    private Locale locale;

    public MessageService(MessageSource messageSource, ExamAppProperties properties) {
        this.messageSource = messageSource;
        this.locale = properties.getLocales().contains(Locale.getDefault()) ? Locale.getDefault() : properties.getLocales().get(0);
    }

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

    /**
     * Возвращает локаль
     * @return локаль
     */
    public Locale getLocale() {
        return locale;
    }
}
