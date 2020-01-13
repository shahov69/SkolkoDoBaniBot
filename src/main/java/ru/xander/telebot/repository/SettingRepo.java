package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xander.telebot.entity.Setting;

/**
 * @author Alexander Shakhov
 */
public interface SettingRepo extends JpaRepository<Setting, Long> {
    Setting findByName(String name);
}
