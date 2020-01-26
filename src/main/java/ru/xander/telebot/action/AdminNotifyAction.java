package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.notification.NotifyData;
import ru.xander.telebot.notification.NotifyEvent;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.BanyaService;
import ru.xander.telebot.util.Utils;

import java.time.Instant;
import java.util.stream.Stream;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminNotifyAction implements Action {
    private final BanyaService banyaService;

    @Autowired
    public AdminNotifyAction(BanyaService banyaService) {
        this.banyaService = banyaService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            if ("/test_notify_all".equals(request.getActionName())) {
                Stream.of(NotifyEvent.getAllEvent())
                        .sorted((event1, event2) -> Long.compare(event2.getLag(), event1.getLag())) // сортируем в порядке хронологии событий
                        .forEach(event -> {
                            sender.sendText(request.getBotChatId(), "[" + event + "]");
                            event.getHandler().handle(sender, new NotifyData(request.getBotChatId(), null, null, false));
                        });
            } else {
                Banya banya = banyaService.getBanyaForActiveChat(request);
                if (banya == null) {
                    sender.sendText(request.getBotChatId(), "Баня для чата " + request.getChatId() + " '" + request.getChatTitle() + "' не создана");
                    return;
                }

                if (banya.getStart() == null) {
                    sender.sendText(request.getBotChatId(), "Время начала баньеи для чата " + request.getChatId() + " '" + request.getChatTitle() + "' не установлено");
                    return;
                }

                long banyaStart = banya.getStart().toEpochMilli();
                long currentTime = System.currentTimeMillis();
                long delay = banyaStart - currentTime;

                Stream.of(NotifyEvent.getAllEvent())
                        .filter(event -> event.getLag() < delay)
                        .sorted((event1, event2) -> Long.compare(event2.getLag(), event1.getLag())) // сортируем в порядке хронологии событий
                        .forEach(event -> {
                            long millis = currentTime + delay - event.getLag();
                            Instant date = Utils.createDate(millis);
                            sender.sendText(request.getBotChatId(), Utils.formatDate(date, "yyyy'-'MM'-'dd HH':'mm':'ss'.'SSS") + " [" + event + "]");
                            event.getHandler().handle(sender, new NotifyData(request.getBotChatId(), null, null, false));
                        });
            }
        }
    }
}
