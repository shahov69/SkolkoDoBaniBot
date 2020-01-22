package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xander.telebot.entity.Ilushizm;

/**
 * @author Alexander Shakhov
 */
public interface IlushizmRepo extends JpaRepository<Ilushizm, Long> {
}
