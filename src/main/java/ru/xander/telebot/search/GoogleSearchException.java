package ru.xander.telebot.search;

/**
 * @author Alexander Shakhov
 */
public class GoogleSearchException extends RuntimeException {
    GoogleSearchException(String message) {
        super(message);
    }

    GoogleSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
