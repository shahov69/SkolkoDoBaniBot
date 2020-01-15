package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

/**
 * @author Alexander Shakhov
 */
@Component
public class ExceptionActon implements Action {
    private Throwable exception;

    public ExceptionActon setException(Throwable exception) {
        this.exception = exception;
        return this;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (!request.isBotChat()) {
            sender.sendSticker(request.getChatId(), "CAADBQADcwMAAukKyANgmywamaobRwI");
        }
        String message = String.format(
                "Exception from chat '%s':\n%s",
                request.getChatTitle(), Utils.stackTraceToString(exception));
        sender.sendText(request.getBotChatId(), message);
    }
}
