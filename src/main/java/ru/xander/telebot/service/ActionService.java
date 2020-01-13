package ru.xander.telebot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.xander.telebot.action.Action;
import ru.xander.telebot.action.DocumentAction;
import ru.xander.telebot.action.FirstTimeAction;
import ru.xander.telebot.action.PhotoAction;
import ru.xander.telebot.action.StickerAction;
import ru.xander.telebot.action.UnknownAction;
import ru.xander.telebot.action.VideoAction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Service
public class ActionService {

    @Value("${telegram.bot.userId}")
    private Integer botUserId;
    @Value("${telegram.bot.chatId}")
    private Long botChatId;

    private final Map<Class<? extends Action>, Action> actionMap;

    @Autowired
    public ActionService(List<Action> actionList) {
        log.info("Found {} actions", actionList.size());
        this.actionMap = actionList.stream().collect(Collectors.toMap(Action::getClass, a -> a));
    }

    public Action resolveAction(Update update) {
        Message message = update.getMessage();
        if (isFirstTime(message)) {
            return actionMap.get(FirstTimeAction.class);
        }
        if (message.getChatId().equals(botChatId)) {
            return getAdminAction(message);
        } else {
            return getUserAction();
        }
    }

    private Action getAdminAction(Message message) {
        if (message.getSticker() != null) {
            return actionMap.get(StickerAction.class);
        }
        if (message.getVideo() != null) {
            return actionMap.get(VideoAction.class);
        }
        if (message.getDocument() != null) {
            return actionMap.get(DocumentAction.class);
        }
        List<PhotoSize> photo = message.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            return actionMap.get(PhotoAction.class);
        }
        return null;
    }

    private Action getUserAction() {
        return actionMap.get(UnknownAction.class);
    }

    private boolean firstTimeChecked = false;

    private boolean isFirstTime(Message message) {
        if (firstTimeChecked) {
            return true;
        }
        firstTimeChecked = true;
        if (message.getNewChatMembers() != null) {
            for (User newUser : message.getNewChatMembers()) {
                if (newUser.getId().equals(botUserId)) {
                    return true;
                }
            }
        }
        return false;
    }
}