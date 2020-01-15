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
     * Стикер при успешной установке картинки
     */
    STICKER_PICTURE_SET,
    /**
     * Текст при успешной установке картинки
     */
    TEXT_PICTURE_SET,
    /**
     * Текст при неопределенной команде
     */
    TEXT_UNKNOWN
}
