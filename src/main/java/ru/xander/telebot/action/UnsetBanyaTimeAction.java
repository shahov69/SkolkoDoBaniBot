package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.service.BanyaService;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

import java.time.Instant;

/**
 * @author Alexander Shakhov
 */
@Component
public class UnsetBanyaTimeAction implements Action {
    private final BanyaService banyaService;

    @Autowired
    public UnsetBanyaTimeAction(BanyaService banyaService) {
        this.banyaService = banyaService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (!request.isSuperUser()) {
            sender.sendText(request.getChatId(), "Дохуя умный что ли?");
            return;
        }

        Banya banya = banyaService.getBanya(request, true);
        if (banya.getStart() == null) {
            sender.sendText(request.getChatId(), "Время баньки ещё не установлено, ебабоба");
            return;
        }

        try {
            Instant banyaStart = banya.getStart();
            Instant banyaEnd = banya.getFinish();
            banya.setStart(null);
            banya.setFinish(null);
            banyaService.save(banya);

            sender.sendText(request.getChatId(), String.format(
                    "Время баньки было с %s по %s, а теперь хуй",
                    Utils.formatDate(banyaStart, "yyyy'-'MM'-'dd HH':'mm':'ss'.'SSS"),
                    Utils.formatDate(banyaEnd, "yyyy'-'MM'-'dd HH':'mm':'ss'.'SSS")));
        } catch (Exception e) {
            sender.sendText(request.getChatId(), "Произошла неведомая хуйня \uD83D\uDE2C");
            throw e;
        }
    }
}
