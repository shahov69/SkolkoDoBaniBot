package ru.xander.telebot.action;

import ru.xander.telebot.util.Request;

/**
 * @author Alexander Shakhov
 */
public interface Action {
    void execute(Request request);
}
