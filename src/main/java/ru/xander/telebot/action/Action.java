package ru.xander.telebot.action;

import ru.xander.telebot.dto.Request;
import ru.xander.telebot.sender.Sender;

/**
 * @author Alexander Shakhov
 */
public interface Action {
    void execute(Request request, Sender sender);
}
