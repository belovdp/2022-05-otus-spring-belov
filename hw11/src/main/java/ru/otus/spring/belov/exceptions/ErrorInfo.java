package ru.otus.spring.belov.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class ErrorInfo {

    private final String status;
    private final String message;
    private final String details;
}
