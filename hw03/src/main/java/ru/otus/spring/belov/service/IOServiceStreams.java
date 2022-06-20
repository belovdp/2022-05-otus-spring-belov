package ru.otus.spring.belov.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Сервис ввода вывода информации
 */
public class IOServiceStreams implements IOService {

    /** Поток вывода информации */
    private final PrintStream output;
    /** Поток вывода информации с ошибками */
    private final PrintStream errorOutput;
    /** Поток ввода */
    private final Scanner input;
    /** Компонент локализаци */
    private final MessageService messageService;

    /**
     * Конструктор
     * @param output         поток вывода информации
     * @param errorOutput    поток вывода информации с ошибками
     * @param input          поток ввода
     * @param messageService сервис работы с сообщениями
     */
    public IOServiceStreams(PrintStream output, PrintStream errorOutput, InputStream input, MessageService messageService) {
        this.output = output;
        this.errorOutput = errorOutput;
        this.messageService = messageService;
        this.input = new Scanner(input);
    }

    @Override
    public void print(String message) {
        output.println(message);
    }

    @Override
    public void printDelimiter() {
        print("---------------------------------");
    }

    @Override
    public void printError(String message) {
        errorOutput.println(message);
    }

    @Override
    public String readAnswer() {
        return input.nextLine();
    }

    @Override
    public int readIntAnswer() {
        try {
            return Integer.parseInt(readAnswer());
        } catch (NumberFormatException numberFormatException) {
            printError(messageService.getMessage("exam.answer.read_int"));
            return readIntAnswer();
        }
    }
}
