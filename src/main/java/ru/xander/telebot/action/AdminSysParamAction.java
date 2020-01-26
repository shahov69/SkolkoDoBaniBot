package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.SettingService;

import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminSysParamAction implements Action {
    private final SettingService settingService;

    @Autowired
    public AdminSysParamAction(SettingService settingService) {
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            boolean def = "/sysdefault".equals(request.getActionName());
            String text = settingService.getAll()
                    .stream()
                    .map(s -> "----------------------------------------\n"
                            + "/setparam " + s.getName() + " " + (def ? s.getDefaultValue() : s.getValue()))
                    .collect(Collectors.joining("\n"));
            sender.sendText(request.getBotChatId(), text);
        }
    }
}
