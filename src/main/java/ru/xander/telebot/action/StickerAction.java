package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.service.SenderService;

/**
 * @author Alexander Shakhov
 */
@Component
public class StickerAction implements Action {
    @Autowired
    private SenderService sender;

    @Override
    public void execute(Request request) {
        if (request.isBotChat()) {
            sender.sendText(request.getBotChatId(), "/ss " + request.getStickerId());
        }
    }
}
