package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminSetParamAction implements Action {

    private final SettingService settingService;

    @Autowired
    public AdminSetParamAction(SettingService settingService) {
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String text = request.getText();

            int space = text.indexOf(' ');
            if (space < 0) {
                sender.sendText(request.getBotChatId(), "какая-то хуйня");
                return;
            }

            String paramName = text.substring(0, space);
            String paramValue = text.substring(space + 1);

            if (paramValue.equalsIgnoreCase("null")) {
                paramValue = null;
            }

            settingService.saveParam(paramName, paramValue);

            if (paramValue == null) {
                sender.sendText(request.getBotChatId(), "параметру " + paramName + " установлено пустое значение");
            } else {
                sender.sendText(request.getBotChatId(), "значение параметра " + paramName + " установлено");
            }
        }
    }
}
