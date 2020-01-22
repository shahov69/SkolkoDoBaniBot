package ru.xander.telebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.Setting;
import ru.xander.telebot.repository.SettingRepo;
import ru.xander.telebot.util.BotException;
import ru.xander.telebot.util.Utils;

import java.util.List;
import java.util.Objects;

import static ru.xander.telebot.dto.SettingName.ACTIVE_CHAT_ID;
import static ru.xander.telebot.dto.SettingName.ADMIN_ID;

/**
 * @author Alexander Shakhov
 */
@Service
public class SettingService {
    private final SettingRepo settingRepo;

    @Autowired
    public SettingService(SettingRepo settingRepo) {
        this.settingRepo = settingRepo;
    }

    public List<Setting> getAll() {
        return settingRepo.findAll();
    }

    public String getString(SettingName settingName) {
        Setting setting = settingRepo.findByName(settingName);
        if (setting == null) {
            return null;
        }
        return setting.getValue();
    }

    public Integer getInt(SettingName settingName) {
        String value = getString(settingName);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }

    public Long getLong(SettingName settingName) {
        String value = getString(settingName);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Long.parseLong(value);
    }

    public <T> T getJson(SettingName settingName, Class<T> clazz) {
        String value = getString(settingName);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Utils.parseJson(value, clazz);
    }

    public Long getActiveChatId() {
        Setting activeChatId = settingRepo.findByName(ACTIVE_CHAT_ID);
        if (activeChatId == null) {
            throw new BotException("Установи ACTIVE_CHAT_ID!!!");
        }
        return Long.parseLong(activeChatId.getValue());
    }

    public boolean checkPermission(Request request) {
        if (request.isSuperUser()) {
            return true;
        }
        Setting adminId = settingRepo.findByName(ADMIN_ID);
        if (adminId == null) {
            return false;
        }
        return Objects.equals(request.getUserId(), Integer.parseInt(adminId.getValue()));
    }
}
