package ru.otus.spring.belov.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса комуникации с пользователем")
@ExtendWith(MockitoExtension.class)
class IOServiceStreamsTest {

    @Mock
    private MessageService messageService;

    @Test
    @SneakyThrows
    @DisplayName("Тест считывания ответа пользователя из консоли")
    public void readAnswerTest() {
        var answers = "test input";
        try (var bais = new ByteArrayInputStream(answers.getBytes())) {
            var service = new IOServiceStreams(null, null, bais, null);
            var answer = service.readAnswer();
            assertEquals(answers, answer, "Ответы не совпадают");
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("Тест считывания числового ответа. Проверяет что если не цифра, заставляем считывать ещё раз")
    public void readIntAnswerTest() {
        var answers = "test\ninput\nt\nsd\ngg\n1";
        try (var bais = new ByteArrayInputStream(answers.getBytes())) {
            var service = spy(new IOServiceStreams(null, System.out, bais, messageService));
            var answer = service.readIntAnswer();
            assertEquals(1, answer, "Ответы не совпадают");
            verify(service, times(6)).readIntAnswer();
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("Тест вывода сообщения пользователю в консоль")
    public void printTest() {
        var message = "test message";
        try (var baos = new ByteArrayOutputStream(); var printStream = new PrintStream(baos)) {
            var service = new IOServiceStreams(printStream, null, System.in, null);
            service.print(message);
            assertEquals(message, baos.toString().trim());
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("Тест вывода сообщения пользователю в консоль")
    public void printErrorTest() {
        String message = "test message";
        try (var baos = new ByteArrayOutputStream(); var printStream = new PrintStream(baos)) {
            var service = new IOServiceStreams(null, printStream, System.in, null);
            service.printError(message);
            assertEquals(message, baos.toString().trim());
        }
    }
}