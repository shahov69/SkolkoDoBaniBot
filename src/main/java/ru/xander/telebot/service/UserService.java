package ru.xander.telebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.User;
import ru.xander.telebot.repository.UserRepo;
import ru.xander.telebot.util.BotException;

import java.util.List;
import java.util.Optional;

/**
 * @author Alexander Shakhov
 */
@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User save(Request request) {
        org.telegram.telegrambots.meta.api.objects.User telegramUser = request.getMessage().getFrom();
        Optional<User> userOptional = userRepo.findById(telegramUser.getId());
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        User user = new User();
        user.setId(telegramUser.getId());
        user.setFirstName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setUserName(telegramUser.getUserName());
        user.setIsBot(telegramUser.getBot());
        user.setLangCode(telegramUser.getLanguageCode());
        return userRepo.save(user);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public void updateCityId(int userId, int cityId) {
        Optional<User> optional = userRepo.findById(userId);
        if (!optional.isPresent()) {
            throw new BotException("Пользователь с ID = " + userId + " не найден");
        }

        User user = optional.get();
        user.setCityId(cityId);
        userRepo.save(user);
    }
}
