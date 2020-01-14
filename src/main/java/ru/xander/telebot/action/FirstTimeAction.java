package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class FirstTimeAction implements Action {
    @Override
    public void execute(Request request, Sender sender) {
        sender.sendText(0L, "First Time Action");
    }
}
