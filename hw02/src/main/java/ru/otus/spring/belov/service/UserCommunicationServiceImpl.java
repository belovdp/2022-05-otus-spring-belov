package ru.otus.spring.belov.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UserCommunicationServiceImpl implements UserCommunicationService {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String message) {
        System.err.println(message);
    }

    @Override
    public String readAnswer() {
        return scanner.nextLine();
    }
}
