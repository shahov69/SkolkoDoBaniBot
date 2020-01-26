package ru.xander.telebot.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alexander Shakhov
 */
@Getter
@AllArgsConstructor
public class NotifyData {
    private final Long chatId;
    private final String howMuchTemplate;
    private final Long nanos;
    private final boolean showHowMuch;
}
