package ru.otus.spring.belov.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест сервиса комуникации с пользователем")
class UserCommunicationServiceImplTest {

    @Test
    @SneakyThrows
    @DisplayName("Тест считывания ответа пользователя из консоли")
    public void readAnswerTest() {
        String answers = "test input";
        InputStream stdin = System.in;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(answers.getBytes())) {
            System.setIn(bais);
            UserCommunicationServiceImpl service = new UserCommunicationServiceImpl();
            String answer = service.readAnswer();
            assertEquals(answers, answer, "Ответы не совпадают");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("Тест вывода сообщения пользователю в консоль")
    public void printTest() {
        String message = "test message";
        PrintStream originalPrintStream = System.out;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(baos)) {
            System.setOut(printStream);
            UserCommunicationServiceImpl service = new UserCommunicationServiceImpl();
            service.print(message);
            assertEquals(message, baos.toString().trim());
        } finally {
            System.setOut(originalPrintStream);
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("Тест вывода сообщения пользователю в консоль")
    public void printErrorTest() {
        String message = "test message";
        PrintStream originalPrintStream = System.err;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(baos)) {
            System.setErr(printStream);
            UserCommunicationServiceImpl service = new UserCommunicationServiceImpl();
            service.printError(message);
            assertEquals(message, baos.toString().trim());
        } finally {
            System.setErr(originalPrintStream);
        }
    }
}