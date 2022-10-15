package ru.otus.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.core.GenericSelector;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Human;
import ru.otus.homework.enums.Job;

import java.util.Random;

/**
 * Фильтр человеков.
 * Кажется тут должна быть сложная логика отбора, но практика показывает что её нет
 */
@Component
@Slf4j
public class MobilizationFilter implements GenericSelector<Human> {

    /** Приблизительный процент к мобилизации */
    private static final int PERCENT_TO_MOBILIZATION = 1;
    /** Рандомайзер */
    private static final Random RANDOM = new Random();

    @Override
    public boolean accept(Human human) {
        if (human.isJobReservation()) {
            log.info("{}. Есть бронь", human.getName());
            return false;
        }
        if (human.getJob() == Job.SOLDIER) {
            log.info("{}. Уже служит", human.getName());
            return false;
        }
        // А дальше как повезет
        boolean accept = RANDOM.nextInt(100) <= PERCENT_TO_MOBILIZATION;
        log.info("{}. {}", human.getName(), accept ? "Не повезло" : "Повезло");
        return accept;
    }
}
