package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.belov.component.LocaleHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@DisplayName("Тест сервиса работы с сообщениями")
@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;
    @MockBean
    private LocaleHolder localeHolder;

    @Test
    @DisplayName("Тест вывода с разыми локалями")
    public void changeLocaleTest() {
        when(localeHolder.getLocale()).thenReturn(Locale.ENGLISH, new Locale("ru"));
        assertEquals("User: 123 asd", messageService.getMessage("exam.result.user", 123, "asd"));
        assertEquals("Пользователь: 123 asd", messageService.getMessage("exam.result.user", 123, "asd"));
    }
}