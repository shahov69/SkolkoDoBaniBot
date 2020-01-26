package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.util.Sender;

@Component
public class AdminSendAction implements Action {
    private final SettingService settingService;

    @Autowired
    public AdminSendAction(SettingService settingService) {
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            Long activeChatId = settingService.getActiveChatId();
            if (request.getActionName().startsWith("/st ")) {
                sendText(activeChatId, request, sender, MessageMode.PLAIN);
            } else {
                String[] params = request.getActionName().split("_");
                switch (params[0]) {
                    case "/st":
                        if (params[1].equals("html")) {
                            sendText(activeChatId, request, sender, MessageMode.HTML);
                        } else {
                            sendText(activeChatId, request, sender, MessageMode.MARKDOWN);
                        }
                        break;
                    case "/ss":
                        sendSticker(activeChatId, params[1], request, sender);
                        break;
                    case "/sp":
                        sendPicture(activeChatId, params[1], request, sender);
                        break;
                    case "/sv":
                        sendVideo(activeChatId, params[1], request, sender);
                        break;
                    case "/sd":
                        sendDocument(activeChatId, params[1], request, sender);
                        break;
                    default:
                        sender.sendText(request.getBotChatId(), "чото непонятное '" + request.getActionName() + "'");
                        break;
                }
            }
        }
    }

    private void sendText(Long chatId, Request request, Sender sender, MessageMode messageMode) {
        String text = request.getText();
        if (text.isEmpty()) {
            sender.sendText(request.getBotChatId(), "надо бы чото сказать!");
            return;
        }
        sender.sendText(chatId, text, messageMode);
    }

    private void sendSticker(Long chatId, String stickerId, Request request, Sender sender) {
        if (StringUtils.isEmpty(stickerId)) {
            sender.sendText(request.getBotChatId(), "надо бы указать стикер id");
            return;
        }
        sender.sendSticker(chatId, stickerId.trim());
    }

    private void sendPicture(Long chatId, String pictureId, Request request, Sender sender) {
        if (StringUtils.isEmpty(pictureId)) {
            sender.sendText(request.getBotChatId(), "надо бы указать пикча id");
            return;
        }
        sender.sendPicture(chatId, pictureId);
    }

    private void sendVideo(Long chatId, String videoId, Request request, Sender sender) {
        if (StringUtils.isEmpty(videoId)) {
            sender.sendText(request.getBotChatId(), "надо бы указать видео id");
            return;
        }
        sender.sendVideo(chatId, videoId.trim());
    }

    private void sendDocument(Long chatId, String documentId, Request request, Sender sender) {
        if (StringUtils.isEmpty(documentId)) {
            sender.sendText(request.getBotChatId(), "надо бы указать документ id");
            return;
        }
        sender.sendVideo(chatId, documentId.trim());
    }
}
