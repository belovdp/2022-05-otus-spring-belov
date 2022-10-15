package ru.otus.homework.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.domain.Human;

import java.util.Collection;

/**
 * Частичная мобилизация
 */
@MessagingGateway
public interface PartialMobilization {

    @Gateway(requestChannel = "humansChannel", replyChannel = "soldiersChannel")
    Collection<Human> start(Collection<Human> order);
}
