package ru.xander.telebot.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
public class Request {
    private Long chatId;
    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String rawMessage;
}
