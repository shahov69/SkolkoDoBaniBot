package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Video;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminVideoAction implements Action {
    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            Video video = request.getMessage().getVideo();
            String message = String.format(
                    "/sv_%s\nWidth: %d, Height: %d\nDuration: %d, Size: %d\nMime: %s",
                    video.getFileId(),
                    video.getWidth(),
                    video.getHeight(),
                    video.getDuration(),
                    video.getFileSize(),
                    video.getMimeType());
            sender.sendText(request.getBotChatId(), message);
        }
    }
}
