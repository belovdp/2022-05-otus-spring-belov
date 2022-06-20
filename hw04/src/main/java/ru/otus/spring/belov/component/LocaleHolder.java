package ru.otus.spring.belov.component;

import java.util.Locale;

/**
 * Контракт работы с локалью
 */
public interface LocaleHolder {

    /**
     * Изменяет локаль
     * @param locale локаль
     */
    void changeLocale(Locale locale);

    /**
     * Возвращает текущую локаль
     * @return локаль
     */
    Locale getLocale();
}
