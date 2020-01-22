package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xander.telebot.entity.Kirushizm;

/**
 * @author Alexander Shakhov
 */
public interface KirushizmRepo extends JpaRepository<Kirushizm, Long> {
}
