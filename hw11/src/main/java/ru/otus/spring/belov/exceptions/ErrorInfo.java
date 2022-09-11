package ru.otus.spring.belov.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Информация об ошибке
 */
@RequiredArgsConstructor
@Getter
public class ErrorInfo {

    private final String status;
    private final String message;
}
