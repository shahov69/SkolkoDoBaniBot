package ru.xander.telebot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.xander.telebot.action.Action;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.service.ActionService;
import ru.xander.telebot.service.SenderService;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Component
public class SkolkoDoBaniBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.chatId}")
    private Long botChatId;

    private final ActionService actionService;

    @Autowired
    public SkolkoDoBaniBot(ActionService actionService) {
        log.info("Start bot...");
        this.actionService = actionService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Action action = actionService.resolveAction(update);
        if (action != null) {
            Request request = fromUpdate(update);
            action.execute(request);
        }
    }

    @Override
    public void onClosing() {
        log.info("On closing.");
        super.onClosing();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Bean
    public SenderService getSenderService() {
        return new SenderService(this);
    }

    private Request fromUpdate(Update update) {
        final Message message = update.getMessage();
        final Chat chat = message.getChat();
        final User user = message.getFrom();

        Request request = new Request();
        request.setMessage(message);
        request.setUserId(user.getId());
        request.setChatId(chat.getId());
        request.setRawMessage(message.getText());
        request.setBotChatId(botChatId);
        return request;
    }
}
