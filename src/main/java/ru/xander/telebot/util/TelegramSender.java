package ru.xander.telebot.util;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.xander.telebot.dto.MessageMode;

import java.io.InputStream;

/**
 * @author Alexander Shakhov
 */
@SuppressWarnings({"DuplicatedCode"})
public class TelegramSender implements Sender {

    private final AbsSender sender;

    public TelegramSender(AbsSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendText(Long chatId, String text) {
        sendText(chatId, text, null, MessageMode.PLAIN);
    }

    @Override
    public void sendText(Long chatId, String text, Integer replyTo) {
        sendText(chatId, text, replyTo, MessageMode.PLAIN);
    }

    @Override
    public void sendText(Long chatId, String text, MessageMode messageMode) {
        sendText(chatId, text, null, messageMode);
    }

    @Override
    public void sendText(Long chatId, String text, Integer replyTo, MessageMode messageMode) {
        SendMessage send = new SendMessage();
        send.setChatId(chatId);
        send.setText(EmojiParser.parseToUnicode(text));
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        if (messageMode == MessageMode.MARKDOWN) {
            send.enableMarkdown(true);
        }
        if (messageMode == MessageMode.HTML) {
            send.enableHtml(true);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSticker(Long chatId, String stickerId) {
        sendSticker(chatId, stickerId, (Integer) null);
    }

    @Override
    public void sendSticker(Long chatId, String stickerId, Integer replyTo) {
        SendSticker send = new SendSticker();
        send.setChatId(chatId);
        send.setSticker(stickerId);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSticker(Long chatId, String stickerName, InputStream sticker) {
        sendSticker(chatId, stickerName, sticker, null);
    }

    @Override
    public void sendSticker(Long chatId, String stickerName, InputStream sticker, Integer replyTo) {
        SendSticker send = new SendSticker();
        send.setChatId(chatId);
        send.setSticker(stickerName, sticker);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPicture(Long chatId, String photoId) {
        sendPicture(chatId, photoId, (String) null, null, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoId, Integer replyTo) {
        sendPicture(chatId, photoId, (String) null, replyTo, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoId, MessageMode messageMode) {
        sendPicture(chatId, photoId, (String) null, null, messageMode);
    }

    @Override
    public void sendPicture(Long chatId, String photoId, Integer replyTo, MessageMode messageMode) {
        sendPicture(chatId, photoId, (String) null, replyTo, messageMode);
    }

    @Override
    public void sendPicture(Long chatId, String photoId, String caption) {
        sendPicture(chatId, photoId, caption, null, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoId, String caption, Integer replyTo) {
        sendPicture(chatId, photoId, caption, replyTo, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoId, String caption, MessageMode messageMode) {
        sendPicture(chatId, photoId, caption, null, messageMode);
    }

    @Override
    public void sendPicture(Long chatId, String photoId, String caption, Integer replyTo, MessageMode messageMode) {
        SendPhoto send = new SendPhoto();
        send.setChatId(chatId);
        send.setPhoto(photoId);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        if (caption != null) {
            send.setCaption(caption);
        }
        if (messageMode == MessageMode.MARKDOWN) {
            send.setParseMode(ParseMode.MARKDOWN);
        }
        if (messageMode == MessageMode.HTML) {
            send.setParseMode(ParseMode.HTML);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo) {
        sendPicture(chatId, photoName, photo, null, null, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo, Integer replyTo) {
        sendPicture(chatId, photoName, photo, null, replyTo, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo, MessageMode messageMode) {
        sendPicture(chatId, photoName, photo, null, null, messageMode);
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo, Integer replyTo, MessageMode messageMode) {
        sendPicture(chatId, photoName, photo, null, replyTo, messageMode);
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo, String caption) {
        sendPicture(chatId, photoName, photo, caption, null, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo, String caption, Integer replyTo) {
        sendPicture(chatId, photoName, photo, caption, replyTo, MessageMode.PLAIN);
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo, String caption, MessageMode messageMode) {
        sendPicture(chatId, photoName, photo, caption, null, messageMode);
    }

    @Override
    public void sendPicture(Long chatId, String photoName, InputStream photo, String caption, Integer replyTo, MessageMode messageMode) {
        SendPhoto send = new SendPhoto();
        send.setChatId(chatId);
        send.setPhoto(photoName, photo);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        if (caption != null) {
            send.setCaption(caption);
        }
        if (messageMode == MessageMode.MARKDOWN) {
            send.setParseMode(ParseMode.MARKDOWN);
        }
        if (messageMode == MessageMode.HTML) {
            send.setParseMode(ParseMode.HTML);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVideo(Long chatId, String videoId) {
        sendVideo(chatId, videoId, (String) null, null);
    }

    @Override
    public void sendVideo(Long chatId, String videoId, String caption) {
        sendVideo(chatId, videoId, caption, null);
    }

    @Override
    public void sendVideo(Long chatId, String videoId, Integer replyTo) {
        sendVideo(chatId, videoId, (String) null, replyTo);
    }

    @Override
    public void sendVideo(Long chatId, String videoId, String caption, Integer replyTo) {
        SendVideo send = new SendVideo();
        send.setChatId(chatId);
        send.setVideo(videoId);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        if (caption != null) {
            send.setCaption(caption);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVideo(Long chatId, String videoName, InputStream video) {
        sendVideo(chatId, videoName, video, null, null);
    }

    @Override
    public void sendVideo(Long chatId, String videoName, InputStream video, String caption) {
        sendVideo(chatId, videoName, video, caption, null);
    }

    @Override
    public void sendVideo(Long chatId, String videoName, InputStream video, Integer replyTo) {
        sendVideo(chatId, videoName, video, null, replyTo);
    }

    @Override
    public void sendVideo(Long chatId, String videoName, InputStream video, String caption, Integer replyTo) {
        SendVideo send = new SendVideo();
        send.setChatId(chatId);
        send.setVideo(videoName, video);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        if (caption != null) {
            send.setCaption(caption);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendDocument(Long chatId, String documentId) {
        sendDocument(chatId, documentId, (String) null, null);
    }

    @Override
    public void sendDocument(Long chatId, String documentId, String caption) {
        sendDocument(chatId, documentId, caption, null);
    }

    @Override
    public void sendDocument(Long chatId, String documentId, Integer replyTo) {
        sendDocument(chatId, documentId, (InputStream) null, replyTo);
    }

    @Override
    public void sendDocument(Long chatId, String documentId, String caption, Integer replyTo) {
        SendDocument send = new SendDocument();
        send.setChatId(chatId);
        send.setDocument(documentId);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        if (caption != null) {
            send.setCaption(caption);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendDocument(Long chatId, String documentName, InputStream document) {
        sendDocument(chatId, documentName, document, null, null);
    }

    @Override
    public void sendDocument(Long chatId, String documentName, InputStream document, String caption) {
        sendDocument(chatId, documentName, document, caption, null);
    }

    @Override
    public void sendDocument(Long chatId, String documentName, InputStream document, Integer replyTo) {
        sendDocument(chatId, documentName, document, null, replyTo);
    }

    @Override
    public void sendDocument(Long chatId, String documentName, InputStream document, String caption, Integer replyTo) {
        SendDocument send = new SendDocument();
        send.setChatId(chatId);
        send.setDocument(documentName, document);
        if (replyTo != null) {
            send.setReplyToMessageId(replyTo);
        }
        if (caption != null) {
            send.setCaption(caption);
        }
        try {
            sender.execute(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
