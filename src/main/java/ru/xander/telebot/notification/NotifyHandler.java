package ru.xander.telebot.notification;

import ru.xander.telebot.sender.Sender;

/**
 * @author Alexander Shakhov
 */
public interface NotifyHandler {
    void handle(Sender sender, NotifyData data);
}
