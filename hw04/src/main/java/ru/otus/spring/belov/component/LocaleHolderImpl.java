package ru.otus.spring.belov.component;

import org.springframework.stereotype.Component;
import ru.otus.spring.belov.config.ExamAppProperties;

import java.util.Locale;

/**
 * Класс работы с локалью
 */
@Component
public class LocaleHolderImpl implements LocaleHolder {

    /** Текущая локаль */
    private Locale locale;

    /**
     * Конструктор
     * @param properties свойства приложения
     */
    public LocaleHolderImpl(ExamAppProperties properties) {
        this.locale = properties.getLocales().contains(Locale.getDefault()) ?
                Locale.getDefault() : properties.getLocales().get(0);
    }

    @Override
    public void changeLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
