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

    private final ActionService actionService;
    private final Sender sender;

    @Autowired
    public SkolkoDoBaniBot(ActionService actionService) {
        log.info("Start bot...");
        this.actionService = actionService;
        this.sender = new TelegramSender(this);
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

}
