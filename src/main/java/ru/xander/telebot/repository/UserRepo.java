package ru.xander.telebot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xander.telebot.entity.User;

/**
 * @author Alexander Shakhov
 */
public interface UserRepo extends JpaRepository<User, Integer> {
}
