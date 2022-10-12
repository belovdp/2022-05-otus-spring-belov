package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.service.PartialMobilizationService;

/**
 * Приложение для мобилизации
 */
@SpringBootApplication
public class MobilizationApp {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(MobilizationApp.class);

        PartialMobilizationService service = ctx.getBean(PartialMobilizationService.class);
        service.startMobilization();
    }
}
