package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Video;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.repository.BanyaRepo;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.util.Sender;

import java.util.Comparator;
import java.util.Optional;

import static ru.xander.telebot.dto.SettingName.STICKER_PICTURE_SET;
import static ru.xander.telebot.dto.SettingName.TEXT_PICTURE_SET;

/**
 * @author Alexander Shakhov
 */
@Component
public class SetPictureAction implements Action {
    @Autowired
    private SettingService settingService;
    @Autowired
    private BanyaRepo banyaRepo;

    @Override
    public void execute(Request request, Sender sender) {
        if (isSetPikcha(request.getCaption())) {
            if (settingService.checkPermission(request)) {
                String contentId = getContentId(request.getMessage());
                if (contentId == null) {
                    sender.sendText(request.getChatId(), "чот хуйня какая-то получается((");
                } else {
                    Banya banya = banyaRepo.findByChatId(request.getChatId());
                    banya.setPicture(contentId);
                    banya.setChatName(request.getChatTitle());
                    banyaRepo.save(banya);

                    String textPictureSet = settingService.getString(TEXT_PICTURE_SET);
                    String stickerPictureSet = settingService.getString(STICKER_PICTURE_SET);
                    sender.sendText(request.getChatId(), textPictureSet);
                    sender.sendSticker(request.getChatId(), stickerPictureSet);
                }
            } else {
                sender.sendText(request.getChatId(), "Хуй тебе!", request.getMessageId());
            }
        }
    }

    private boolean isSetPikcha(String caption) {
        if (StringUtils.isEmpty(caption)) {
            return false;
        }
        return caption.trim().toLowerCase().startsWith("/пикча");
    }

    private String getContentId(Message message) {
        if (message.getPhoto() != null) {
            Optional<PhotoSize> photo = message.getPhoto()
                    .stream()
                    .max(Comparator.comparingInt(PhotoSize::getFileSize));
            if (photo.isPresent()) {
                return "p:" + photo.get().getFileId();
            }
        }
        if (message.getVideo() != null) {
            Video video = message.getVideo();
            return "v:" + video.getFileId();
        }
        if (message.getDocument() != null) {
            return "d:" + message.getDocument().getFileId();
        }
        return null;
    }

}
