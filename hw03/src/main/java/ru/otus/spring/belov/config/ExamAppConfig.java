package ru.otus.spring.belov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.belov.service.IOService;
import ru.otus.spring.belov.service.IOServiceStreams;

/**
 * Конфигурация приложения
 */
@Configuration
public class ExamAppConfig {

    @Bean
    public IOService getIOService() {
        return new IOServiceStreams(System.out, System.err, System.in);
    }
}
