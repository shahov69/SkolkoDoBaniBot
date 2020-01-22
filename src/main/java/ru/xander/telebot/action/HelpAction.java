package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class HelpAction implements Action {
    private final SettingService settingService;

    @Autowired
    public HelpAction(SettingService settingService) {
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        String help = settingService.getString(SettingName.TEXT_HELP);
        sender.sendText(request.getChatId(), help, MessageMode.HTML);
    }
}
