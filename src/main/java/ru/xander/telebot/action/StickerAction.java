package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class StickerAction implements Action {
    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            sender.sendText(request.getBotChatId(), "/ss " + request.getStickerId());
        }
    }
}
