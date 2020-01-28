package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.BanyaService;

import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminChatInfoAction implements Action {
    private final BanyaService banyaService;

    @Autowired
    public AdminChatInfoAction(BanyaService banyaService) {
        this.banyaService = banyaService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String banyasInfo = banyaService.getAll()
                    .stream()
                    .map(b -> b.getChatId() + " - " + b.getChatName() + "\n"
                            + "/setparam " + SettingName.ACTIVE_CHAT_ID + " " + b.getChatId() + "\n")
                    .collect(Collectors.joining("\n"));
            sender.sendText(request.getBotChatId(), banyasInfo);
        }
    }
}
