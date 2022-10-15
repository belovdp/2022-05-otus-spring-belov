package ru.otus.homework.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework.service.HumanToSoldierTransformer;
import ru.otus.homework.service.MobilizationFilter;
import ru.otus.homework.service.PartialMobilizationServiceImpl;
import java.util.List;

/**
 * Конфигурация springIntegration
 */
@RequiredArgsConstructor
@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
@Slf4j
public class IntegrationConfig {

    /** Метод обучения человека */
    private static final String HUMAN_TRAINING = "training";
    /** Метод трансформации в солдата */
    private static final String TRANSFORM_HUMAN_TO_SOLDIER = "transformHumanToSoldier";
    /** Сервис частичной мобилизации */
    private final PartialMobilizationServiceImpl partialMobilizationService;
    /** Сервис трансформации в солдата */
    private final HumanToSoldierTransformer humanToSoldierTransformer;
    /** Фильтр человеков */
    private final MobilizationFilter mobilizationFilter;

    @Bean
    public QueueChannel humansChannel() {
        return MessageChannels.queue(100).get();
    }

    @Bean
    public PublishSubscribeChannel soldiersChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(10).get();
    }

    @Bean
    public IntegrationFlow mobilizationFlow() {
        return IntegrationFlows.from("humansChannel")
                .split()
                    .filter(mobilizationFilter)
                .aggregate(agg -> agg.sendPartialResultOnExpiry(true).groupTimeout(100))
                .log(m -> "Человек подлежащих мобилизации: " + ((List<?>) m.getPayload()).size())
                .split()
                    .handle(partialMobilizationService, HUMAN_TRAINING)
                    .transform(humanToSoldierTransformer, TRANSFORM_HUMAN_TO_SOLDIER)
                .aggregate()
                .channel("soldiersChannel")
                .get();
    }
}
