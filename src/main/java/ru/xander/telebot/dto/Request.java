package ru.xander.telebot.dto;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author Alexander Shakhov
 */
@Getter
public class Request {
    private Message message;
    private Long chatId;
    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String rawMessage;

    public static Request fromUpdate(Update update) {
        final Message message = update.getMessage();
        final Chat chat = message.getChat();
        final User user = message.getFrom();

        Request request = new Request();
        request.message = message;
        request.userId = user.getId();
        request.userName = user.getUserName();
        request.firstName = user.getFirstName();
        request.lastName = user.getLastName();
        request.chatId = chat.getId();
        request.rawMessage = message.getText();
        return request;
    }

}
