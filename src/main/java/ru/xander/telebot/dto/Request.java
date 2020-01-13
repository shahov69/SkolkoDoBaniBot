package ru.xander.telebot.dto;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
public class Request {
    private Message message;
    private Long chatId;
    private Integer userId;
    private String rawMessage;
    private Long botChatId;

    public boolean isBotChat() {
        return Objects.equals(chatId, botChatId);
    }

    public String getStickerId() {
        if (message.getSticker() == null) {
            return null;
        }
        return message.getSticker().getFileId();
    }
}
