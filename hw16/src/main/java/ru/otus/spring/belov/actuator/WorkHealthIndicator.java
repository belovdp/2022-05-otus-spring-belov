package ru.otus.spring.belov.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * Кастомный healthcheck.
 * Ничего лучше не придумал как завязаться на время
 */
@Component
public class WorkHealthIndicator implements HealthIndicator {

    private static final LocalTime START_WORKING_TIME = LocalTime.parse("08:00");
    private static final LocalTime END_WORKING_TIME = LocalTime.parse("20:00");

    @Override
    public Health health() {
        var now = LocalTime.now();
        if (now.isAfter(START_WORKING_TIME) && now.isBefore(END_WORKING_TIME)) {
            return Health.status(Status.UP)
                    .withDetail("message", "Мы работаем в штатном режиме")
                    .build();
        }
        return Health.status(Status.DOWN)
                .withDetail("message", "В данное время мы не доступны")
                .build();
    }
}
