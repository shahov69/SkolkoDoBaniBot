package ru.xander.telebot.dto;

/**
 * @author Alexander Shakhov
 */
public enum TimeOfDay {
    NIGHT("Доброй ночи"),
    MORNING("Доброе утро"),
    AFTERNOON("Добрый день"),
    EVENING("Добрый вечер");

    private final String greeting;

    TimeOfDay(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
