package ru.xander.telebot.util;

/**
 * @author Alexander Shakhov
 */
public class BotException extends RuntimeException {
    public BotException(String message) {
        super(message);
    }
}
