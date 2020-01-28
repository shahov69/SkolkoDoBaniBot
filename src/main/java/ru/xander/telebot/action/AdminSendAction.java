package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.SettingService;

/**
 * @author Alexander Shakhov
 */
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
            if (request.getActionName().equals("/st")) {
                sendText(activeChatId, request, sender, MessageMode.PLAIN);
            } else {
                String text = request.getMessage().getText();
                int under = text.indexOf('_');
                if (under < 0) {
                    sender.sendText(request.getBotChatId(), "Ёбань ёбаная " + text);
                    return;
                }
                String action = text.substring(0, under).toLowerCase();
                String param = text.substring(under + 1).trim();
                switch (action) {
                    case "/st":
                        if (param.equals("html")) {
                            sendText(activeChatId, request, sender, MessageMode.HTML);
                        } else {
                            sendText(activeChatId, request, sender, MessageMode.MARKDOWN);
                        }
                        break;
                    case "/ss":
                        sendSticker(activeChatId, param, request, sender);
                        break;
                    case "/sp":
                        sendPicture(activeChatId, param, request, sender);
                        break;
                    case "/sv":
                        sendVideo(activeChatId, param, request, sender);
                        break;
                    case "/sd":
                        sendDocument(activeChatId, param, request, sender);
                        break;
                    default:
                        sender.sendText(request.getBotChatId(), "чото непонятное '" + text + "'");
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
        sender.sendDocument(chatId, documentId.trim());
    }
}
