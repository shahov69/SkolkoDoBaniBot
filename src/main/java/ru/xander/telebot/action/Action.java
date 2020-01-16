package ru.xander.telebot.action;

import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Setting;
import ru.xander.telebot.repository.SettingRepo;
import ru.xander.telebot.util.BotException;
import ru.xander.telebot.util.Sender;

import java.util.Objects;

import static ru.xander.telebot.dto.SettingName.ACTIVE_CHAT_ID;
import static ru.xander.telebot.dto.SettingName.ADMIN_ID;

/**
 * @author Alexander Shakhov
 */
public interface Action {
    void execute(Request request, Sender sender);

    default boolean checkPermission(Request request, SettingRepo settingRepo) {
        if (request.isSuperUser()) {
            return true;
        }
        Setting adminId = settingRepo.findByName(ADMIN_ID);
        if (adminId == null) {
            return false;
        }
        return Objects.equals(request.getUserId(), adminId.getValueAsInt());
    }

    default Long getActiveChatId(SettingRepo settingRepo) {
        Setting activeChatId = settingRepo.findByName(ACTIVE_CHAT_ID);
        if (activeChatId == null) {
            throw new BotException("Установи ACTIVE_CHAT_ID!!!");
        }
        return activeChatId.getValueAsLong();
    }
}
