package ru.otus.spring.belov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.belov.service.IOService;
import ru.otus.spring.belov.service.IOServiceStreams;

/**
 * Конфигурация приложения
 */
@Configuration
@ComponentScan("ru.otus.spring.belov.*")
@PropertySource("classpath:application.properties")
public class ExamAppConfig {

    @Bean
    public IOService getIOService() {
        return new IOServiceStreams(System.out, System.err, System.in);
    }
}
