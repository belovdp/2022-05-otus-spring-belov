package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Human;
import ru.otus.homework.enums.Job;

import java.math.BigDecimal;

/**
 * Превращает человека в солдата
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class HumanToSoldierTransformerImpl implements HumanToSoldierTransformer {

    @Override
    public Human transformHumanToSoldier(Human human) {
        var oldSalary = human.getSalary();
        human.setJob(Job.SOLDIER);
        human.setSalary(new BigDecimal(5000));
        log.info("{} с зп \"{}\" стал солдатом с зп {}", human.getName(), oldSalary, human.getSalary());
        return human;
    }
}
