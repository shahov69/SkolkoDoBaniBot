package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.crown.CrownExtractor;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.CrownService;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminCrownUpdateAction implements Action {

    private final CrownService crownService;

    @Autowired
    public AdminCrownUpdateAction(CrownService crownService) {
        this.crownService = crownService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            crownService.update(new CrownExtractor().extract());
            sender.sendText(request.getBotChatId(), "информация по короне обновлена");
        }
    }
}
