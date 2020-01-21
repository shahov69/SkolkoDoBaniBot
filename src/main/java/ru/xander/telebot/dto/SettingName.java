package ru.xander.telebot.dto;

/**
 * @author Alexander Shakhov
 */
public enum SettingName {
    /**
     * ID чата для манипуляций из админки
     */
    ACTIVE_CHAT_ID,
    /**
     * ID пользователя, являющего админов в чате
     */
    ADMIN_ID,
    /**
     * Стикер для приветствия
     */
    STICKER_HELLO,
    /**
     * Стикер при успешной установке картинки
     */
    STICKER_PICTURE_SET,
    /**
     * Текст с временем до баньки
     */
    TEXT_HOWMUCH_BEFORE,
    /**
     * Текст с временем в процессе баньки
     */
    TEXT_HOWMUCH_ONAIR,
    /**
     * Текст с временем после баньки
     */
    TEXT_HOWMUCH_AFTER,
    /**
     * Текст при успешной установке картинки
     */
    TEXT_PICTURE_SET,
    /**
     * Текст при неопределенной команде
     */
    TEXT_UNKNOWN,
    /**
     * Тексты для картинки с погодкой
     */
    TEXT_WEATHER
}
