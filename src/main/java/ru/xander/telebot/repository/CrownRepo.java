package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xander.telebot.entity.CrownEntity;

/**
 * @author Alexander Shakhov
 */
public interface CrownRepo extends JpaRepository<CrownEntity, Long> {
}
