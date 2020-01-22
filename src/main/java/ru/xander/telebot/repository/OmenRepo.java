package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xander.telebot.entity.Omen;

/**
 * @author Alexander Shakhov
 */
public interface OmenRepo extends JpaRepository<Omen, Integer> {
}
