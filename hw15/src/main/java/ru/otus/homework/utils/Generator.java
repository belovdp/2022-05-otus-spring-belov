package ru.otus.homework.utils;

import ru.otus.homework.domain.Human;
import ru.otus.homework.enums.Job;
import ru.otus.homework.enums.MilitarySpecialty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Генератор тестовых данных
 */
public class Generator {


    private static final List<MilitarySpecialty> MILITARY_SPECIALTIES_VALUES = List.of(MilitarySpecialty.values());
    private static final List<Job> JOB_VALUES = List.of(Job.values());
    private static final Random RANDOM = new Random();
    private static long id = 0;

    /**
     * Генерирует популяцию людей
     * @return люди
     */
    public static List<Human> generatePopulation() {
        var population = new ArrayList<Human>();
        for (int index = 0; index < 1000; index++) {
            population.add(Human.builder()
                    .job(getRandomJob())
                    .militarySpecialty(getRandomMilitarySpecialty())
                    .name("Человек " + ++id)
                    .salary(new BigDecimal(RANDOM.nextInt(10, 3000) * 100))
                    .build());
        }
        return population;
    }

    /**
     * Возвращает рандомную специальность
     * @return рандомная специальность
     */
    public static MilitarySpecialty getRandomMilitarySpecialty() {
        return MILITARY_SPECIALTIES_VALUES.get(RANDOM.nextInt(MILITARY_SPECIALTIES_VALUES.size()));
    }

    /**
     * Возвращает рандомную работу
     * @return рандомная работа
     */
    private static Job getRandomJob() {
        return JOB_VALUES.get(RANDOM.nextInt(JOB_VALUES.size()));
    }
}
