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
     * Ограничение на выводимый список коронавируса (по умолчанию 50)
     */
    CROWN_LIMIT,
    /**
     * Последняя дата, за которую сохранены данные по коронавирусу.
     */
    CROWN_DAY,
    /**
     * Разрешить использование команд бота
     */
    ENABLE_BOT,
    /**
     * Разрешить илюшизмы
     */
    ENABLE_ILUSHIZM,
    /**
     * Разрешить кирюшизмы
     */
    ENABLE_KIRUSHIZM,
    /**
     * Стикер для приветствия
     */
    STICKER_HELLO,
    /**
     * Стикер при успешной установке картинки
     */
    STICKER_PICTURE_SET,
    /**
     * Текст для справкик
     */
    TEXT_HELP,
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
    TEXT_UNKNOWN
}
