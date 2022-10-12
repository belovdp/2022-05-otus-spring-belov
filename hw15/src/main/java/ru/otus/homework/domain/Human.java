package ru.otus.homework.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.enums.Job;
import ru.otus.homework.enums.MilitarySpecialty;

import java.math.BigDecimal;

/**
 * Человек
 */
@Getter
@Setter
@Builder
public class Human {

    /** Имя человека */
    private String name;
    /** Работа на которой работает */
    private Job job;
    /** Запрлата */
    private BigDecimal salary;
    /** Имеет бронь на работе */
    private boolean jobReservation;
    /** Военная специальность */
    private MilitarySpecialty militarySpecialty;
}
