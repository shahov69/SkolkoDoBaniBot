package ru.xander.telebot.action;

import ru.xander.telebot.dto.Request;

/**
 * @author Alexander Shakhov
 */
public interface Action {
    void execute(Request request);
}
