package ru.xander.telebot.util;

import ru.xander.telebot.dto.MessageMode;

import java.io.InputStream;

/**
 * @author Alexander Shakhov
 */
@SuppressWarnings("unused")
public interface Sender {
    int MAX_MESSAGE_LENGTH = 4096;

    void sendText(Long chatId, String text);

    void sendText(Long chatId, String text, Integer replyTo);

    void sendText(Long chatId, String text, MessageMode messageMode);

    void sendText(Long chatId, String text, Integer replyTo, MessageMode messageMode);

    void sendSticker(Long chatId, String stickerId);

    void sendSticker(Long chatId, String stickerId, Integer replyTo);

    void sendSticker(Long chatId, String stickerName, InputStream sticker);

    void sendSticker(Long chatId, String stickerName, InputStream sticker, Integer replyTo);

    void sendPicture(Long chatId, String photoId);

    void sendPicture(Long chatId, String photoId, Integer replyTo);

    void sendPicture(Long chatId, String photoId, MessageMode messageMode);

    void sendPicture(Long chatId, String photoId, Integer replyTo, MessageMode messageMode);

    void sendPicture(Long chatId, String photoId, String caption);

    void sendPicture(Long chatId, String photoId, String caption, Integer replyTo);

    void sendPicture(Long chatId, String photoId, String caption, MessageMode messageMode);

    void sendPicture(Long chatId, String photoId, String caption, Integer replyTo, MessageMode messageMode);

    void sendPicture(Long chatId, String photoName, InputStream photo);

    void sendPicture(Long chatId, String photoName, InputStream photo, Integer replyTo);

    void sendPicture(Long chatId, String photoName, InputStream photo, MessageMode messageMode);

    void sendPicture(Long chatId, String photoName, InputStream photo, Integer replyTo, MessageMode messageMode);

    void sendPicture(Long chatId, String photoName, InputStream photo, String caption);

    void sendPicture(Long chatId, String photoName, InputStream photo, String caption, Integer replyTo);

    void sendPicture(Long chatId, String photoName, InputStream photo, String caption, MessageMode messageMode);

    void sendPicture(Long chatId, String photoName, InputStream photo, String caption, Integer replyTo, MessageMode messageMode);

    void sendVideo(Long chatId, String videoId);

    void sendVideo(Long chatId, String videoId, String caption);

    void sendVideo(Long chatId, String videoId, Integer replyTo);

    void sendVideo(Long chatId, String videoId, String caption, Integer replyTo);

    void sendVideo(Long chatId, String videoName, InputStream video);

    void sendVideo(Long chatId, String videoName, InputStream video, String caption);

    void sendVideo(Long chatId, String videoName, InputStream video, Integer replyTo);

    void sendVideo(Long chatId, String videoName, InputStream video, String caption, Integer replyTo);

    void sendDocument(Long chatId, String documentId);

    void sendDocument(Long chatId, String documentId, String caption);

    void sendDocument(Long chatId, String documentId, Integer replyTo);

    void sendDocument(Long chatId, String documentId, String caption, Integer replyTo);

    void sendDocument(Long chatId, String documentName, InputStream document);

    void sendDocument(Long chatId, String documentName, InputStream document, String caption);

    void sendDocument(Long chatId, String documentName, InputStream document, Integer replyTo);

    void sendDocument(Long chatId, String documentName, InputStream document, String caption, Integer replyTo);
}
