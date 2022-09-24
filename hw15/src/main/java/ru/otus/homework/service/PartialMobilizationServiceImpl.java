package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.PartialMobilization;
import ru.otus.homework.domain.Human;
import ru.otus.homework.utils.Generator;

import java.util.Collection;

import static ru.otus.homework.utils.Generator.generatePopulation;

/**
 * Сервис частичной мобилизации
 */
@RequiredArgsConstructor
@SuppressWarnings("InfiniteLoopStatement")
@Service
@Slf4j
public class PartialMobilizationServiceImpl implements PartialMobilizationService {

    /** Обработчик частичной мобилизации */
    private final PartialMobilization partialMobilization;

    @Override
    public Human training(Human human) throws InterruptedException {
        log.info("Начало переподготовки человека: {}", human.getName());
        human.setMilitarySpecialty(Generator.getRandomMilitarySpecialty());
        Thread.sleep(500);
        log.info("Конец переподготовки человека: {}. Получена военная специальность: {}. И зарплата: {}",
                human.getName(), human.getMilitarySpecialty(), human.getSalary());
        return human;
    }

    @Override
    public void startMobilization() throws InterruptedException {
        Collection<Human> population = generatePopulation();
        log.warn("Военный ресурс состоит из {} человек.", population.size());
        while (true) {
            Collection<Human> soldiers = partialMobilization.start(population);
            log.warn("Солдаты готовы к отправке. Набролось {} человек.", soldiers.size());
            Thread.sleep(5000);
        }
    }
}
