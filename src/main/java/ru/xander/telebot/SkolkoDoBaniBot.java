package ru.xander.telebot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.sender.TelegramSender;
import ru.xander.telebot.service.ActionService;
import ru.xander.telebot.service.NotifyService;
import ru.xander.telebot.util.Utils;

import javax.annotation.PostConstruct;

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
    private final NotifyService notifyService;
    private final Sender sender;

    @Autowired
    public SkolkoDoBaniBot(ActionService actionService, NotifyService notifyService) {
        log.info("Start bot...");
        this.actionService = actionService;
        this.notifyService = notifyService;
        this.sender = new TelegramSender(this);
    }

    @PostConstruct
    public void init() {
        this.notifyService.schedule(this.sender);
        sendStatus();
    }

    @Override
    public void onUpdateReceived(Update update) {
        actionService.process(update, sender);
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

    private void sendStatus() {
        sender.sendText(botChatId, "Now: " + Utils.now());
    }
}
