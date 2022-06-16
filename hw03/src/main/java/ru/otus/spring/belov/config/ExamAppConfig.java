package ru.otus.spring.belov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.belov.service.IOService;
import ru.otus.spring.belov.service.IOServiceStreams;
import ru.otus.spring.belov.service.MessageService;

/**
 * Конфигурация приложения
 */
@Configuration
public class ExamAppConfig {

    @Bean
    public IOService getIOService(MessageService messageService) {
        return new IOServiceStreams(System.out, System.err, System.in, messageService);
    }
}
