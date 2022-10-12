package ru.otus.homework.service;

import ru.otus.homework.domain.Human;

/**
 * Превращает человека в солдата
 */
public interface HumanToSoldierTransformer {

    /**
     * Превращает человека в солдата
     * @param human человек
     * @return уже солдат
     */
    Human transformHumanToSoldier(Human human);
}
