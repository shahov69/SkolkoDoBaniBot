package ru.xander.telebot.action;

import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;

/**
 * @author Alexander Shakhov
 */
public interface Action {
    void execute(Request request, Sender sender);
}
