package ru.xander.telebot.action;

import io.sentry.Sentry;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.util.BotException;
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
        if (exception instanceof BotException) {
            sender.sendText(request.getBotChatId(), exception.getMessage());
            return;
        }

        Sentry.capture(exception);

        if (!request.isBotChat()) {
            sender.sendSticker(request.getChatId(), "CAADBQADcwMAAukKyANgmywamaobRwI");
        }
        sender.sendText(request.getBotChatId(), "Exception from chat '" + request.getChatTitle() + "': " + exception.toString());
        String stackTrace = Utils.stackTraceToString(exception);
        int parts = (int) (stackTrace.length() / (double) Sender.MAX_MESSAGE_LENGTH);
        for (int part = 0; part < parts; part++) {
            int beginIndex = part * Sender.MAX_MESSAGE_LENGTH;
            int endIndex = Math.min(stackTrace.length(), (part + 1) * Sender.MAX_MESSAGE_LENGTH);
            sender.sendText(request.getBotChatId(), stackTrace.substring(beginIndex, endIndex));
        }
    }
}
