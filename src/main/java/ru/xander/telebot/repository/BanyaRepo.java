package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xander.telebot.entity.Banya;

/**
 * @author Alexander Shakhov
 */
public interface BanyaRepo extends JpaRepository<Banya, Long> {
    Banya findByChatId(Long chatId);
}
