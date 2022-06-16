package ru.otus.spring.belov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Тест сервиса работы с сообщениями")
@SpringBootTest
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Test
    @DisplayName("Тест смены локали")
    public void changeLocaleTest() {
        messageService.changeLocale(Locale.ENGLISH);
        assertEquals("User: 123 asd", messageService.getMessage("exam.result.user", 123, "asd"));
        messageService.changeLocale(new Locale("ru"));
        assertEquals("Пользователь: 123 asd", messageService.getMessage("exam.result.user", 123, "asd"));
    }
}