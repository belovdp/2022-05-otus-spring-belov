package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.belov.component.LocaleHolder;

/**
 * Сервис по работе с сообщениями
 */
@RequiredArgsConstructor
@Component
public class MessageService {

    /** Источник сообщений */
    private final MessageSource messageSource;
    /** Компонент работы с локалью */
    private final LocaleHolder localeHolder;

    /**
     * Возвращает текст сообщений
     * @param key  ключ сообщений
     * @param args аргументы для подстановки в сообщение
     * @return текст сообщений
     */
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localeHolder.getLocale());
    }
}
