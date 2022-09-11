package ru.otus.spring.belov.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

/**
 * Обработчик ошибок
 */
@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleNotFoundException(NotFoundException ex) {
        return new ErrorInfo(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public final ErrorInfo handleBindException(WebExchangeBindException ex) {
        var resultSB = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));
        return new ErrorInfo(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), resultSB);
    }
}
