package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;

import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminPhotoAction implements Action {
    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String message = request
                    .getMessage()
                    .getPhoto()
                    .stream()
                    .map(pz ->
                            String.format(
                                    "/sp_%s\nCaption: %s, File path: %s, File size: %d, WxH: %dx%d",
                                    pz.getFileId(),
                                    request.getCaption(),
                                    pz.getFilePath(),
                                    pz.getFileSize(),
                                    pz.getWidth(),
                                    pz.getHeight()))
                    .collect(Collectors.joining("\n\n"));
            sender.sendText(request.getBotChatId(), message);
        }
    }
}
