package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.service.BanyaService;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

/**
 * @author Alexander Shakhov
 */
@Component
public class SetBanyaTimeAction implements Action {
    private final BanyaService banyaService;

    @Autowired
    public SetBanyaTimeAction(BanyaService banyaService) {
        this.banyaService = banyaService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (!request.isSuperUser()) {
            sender.sendText(request.getChatId(), "Дохуя умный что ли?", request.getMessageId());
            return;
        }

        Banya banya = banyaService.getBanya(request, true);
        if (banya.getStart() != null) {
            sender.sendText(request.getChatId(), "Время баньки уже установлено, ебабоба");
            return;
        }

        String[] args = request.getArgs();
        if (args.length < 2) {
            sender.sendText(request.getChatId(), "Чего-то не хватает");
            return;
        }

        try {
            banya.setStart(Utils.parseDate(args[0], "yyyyMMdd-HHmmss"));
            banya.setFinish(Utils.parseDate(args[1], "yyyyMMdd-HHmmss"));

            if (!banya.getFinish().isAfter(banya.getStart())) {
                sender.sendText(request.getChatId(), "Схуяли startEnd больше endDate?!");
                return;
            }

            banyaService.save(banya);

            sender.sendText(request.getChatId(), String.format(
                    "Заебись! Установил время баньки с %s по %s",
                    Utils.formatDate(banya.getStart(), "yyyy'-'MM'-'dd HH':'mm':'ss'.'SSS"),
                    Utils.formatDate(banya.getFinish(), "yyyy'-'MM'-'dd HH':'mm':'ss'.'SSS")));
            sender.sendSticker(request.getChatId(), "CAADAgADGAEAAuL27QaGl-4DLmPrQAI");
        } catch (Exception e) {
            sender.sendText(request.getChatId(), "Ёбань какая-то случилась \uD83D\uDE31");
            throw e;
        }
    }
}
