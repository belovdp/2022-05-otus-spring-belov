package ru.otus.spring.belov.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {
    /** Имя пользователя */
    private final String firstName;
    /** Фамилия пользователя */
    private final String lastName;
}
