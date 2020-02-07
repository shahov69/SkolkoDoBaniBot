package ru.xander.telebot;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.xander.telebot.crown.CrownRenderer;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.sender.TelegramSender;
import ru.xander.telebot.service.ActionService;
import ru.xander.telebot.service.ForecastService;
import ru.xander.telebot.service.NotifyService;
import ru.xander.telebot.service.SearchService;
import ru.xander.telebot.util.Utils;

import javax.annotation.PostConstruct;
import java.util.List;

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
        List<String> scheduledEvents = this.notifyService.schedule(this.sender);
        sendStatus(scheduledEvents);
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

    private void sendStatus(List<String> scheduledEvents) {
        String statusMessage = prepareStatusMessage(scheduledEvents);
        Sentry.capture(statusMessage);
        sender.sendText(botChatId, statusMessage, MessageMode.HTML);
    }

    private String prepareStatusMessage(List<String> scheduledEvents) {
        return "" +
                "<b>Now: " + Utils.formatDate(Utils.now(), "yyyy'-'MM'-'dd HH':'mm':'ss'.'SSS") + "</b>\n" +
                "<code>" +
                "---------------------------\n" +
                "Forecast service:       " + checkForecastService() + '\n' +
                "Search service:         " + checkSearchService() + '\n' +
                "Crown service:          " + checkCrownService() + "\n" +
                "---------------------------" +
                "</code>\n" +
                "<b>Scheduled events:</b>\n" +
                (scheduledEvents.isEmpty() ? "<i>no events</i>" : String.join("\n", scheduledEvents));
    }

    @Autowired
    private ForecastService forecastService;

    private String checkForecastService() {
        try {
            forecastService.getForecastRender();
            return "✅";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    @Autowired
    private SearchService searchService;

    private String checkSearchService() {
        try {
            searchService.searchDobro();
            return "✅";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    private String checkCrownService() {
        try {
            new CrownRenderer().render();
            return "✅";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }
}
