package ru.xander.telebot.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.xander.telebot.util.Utils;

import java.util.Objects;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
public class Request {
    private Message message;
    private String rawMessage;
    private Integer botUserId;
    private Long botChatId;
    private Integer superUserId;

    public boolean isBotChat() {
        return Objects.equals(getChatId(), botChatId);
    }

    public boolean isSuperUser() {
        return Objects.equals(message.getFrom().getId(), superUserId);
    }

    public boolean isReplyToBot() {
        Message replyToMessage = message.getReplyToMessage();
        if (replyToMessage == null) {
            return false;
        }
        return replyToMessage.getFrom().getId().equals(botUserId);
    }

    public Integer getMessageId() {
        return message.getMessageId();
    }

    public Integer getUserId() {
        return message.getFrom().getId();
    }

    public Long getChatId() {
        return message.getChat().getId();
    }

    public String getChatTitle() {
        return message.getChat().getTitle();
    }

    public String getStickerId() {
        if (message.getSticker() == null) {
            return null;
        }
        return message.getSticker().getFileId();
    }

    public String getUserName() {
        return message.getFrom().getUserName();
    }

    public String getUserFullName() {
        User user = message.getFrom();
        String fullName = user.getFirstName();
        if (!StringUtils.isEmpty(user.getLastName())) {
            fullName = fullName.concat(" ").concat(user.getLastName());
        }
        return fullName;
    }

    public String getCaption() {
        return message.getCaption();
    }

    public String[] getArgs() {
        String text = message.getText();
        if (text == null) {
            return Utils.EMPTY_STRING_ARRAY;
        }
        int firstSpace = text.trim().indexOf(' ');
        if (firstSpace < 0) {
            return Utils.EMPTY_STRING_ARRAY;
        }
        return text.substring(firstSpace + 1).split(" ");
    }
}
