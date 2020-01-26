package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Document;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.sender.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminDocumentAction implements Action {
    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            Document document = request.getMessage().getDocument();
            String message = String.format(
                    "/sd_%s\nFile name: %s\nFile size: %d\nMime Type: %s",
                    document.getFileId(),
                    document.getFileName(),
                    document.getFileSize(),
                    document.getMimeType());
            sender.sendText(request.getBotChatId(), message);
        }
    }
}
