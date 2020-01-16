package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.repository.BanyaRepo;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

import java.time.Instant;

import static ru.xander.telebot.dto.SettingName.*;

/**
 * @author Alexander Shakhov
 */
@Component
public class HowMuchAction implements Action {
    @Autowired
    private SettingService settingService;
    @Autowired
    private BanyaRepo banyaRepo;

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void execute(Request request, Sender sender) {
        Long chatId = getBanyaChatId(request);
        Banya banya = banyaRepo.findByChatId(chatId);

        if ((banya == null) || (banya.getStart() == null)) {
            sender.sendText(request.getChatId(), "Хуй его знает");
            return;
        }

        final String result;
        final Instant now = Utils.now();

        if (banya.getStart().isAfter(now)) {

            if (isShowFuck(request)) {
                sender.sendText(request.getChatId(), ".!.");
                sender.sendSticker(request.getChatId(), "CAADAgADIAAD5NdGDv2HT60jrBWvAg", request.getMessageId());
                return;
            }

            final String template = settingService.getString(TEXT_HOWMUCH_BEFORE);
            long nowNanos = now.toEpochMilli() * 1_000_000L + System.nanoTime() % 1_000_000L;
            long startNanos = banya.getStart().toEpochMilli() * 1_000_000L;
            long nanos = startNanos - nowNanos;
            result = Utils.formatBanyaTime(template, nanos);

        } else if (banya.getFinish().isAfter(now)) {

            final String template = settingService.getString(TEXT_HOWMUCH_ONAIR);
            long nowNanos = now.toEpochMilli() * 1_000_000L + System.nanoTime() % 1_000_000L;
            long startNanos = banya.getStart().toEpochMilli() * 1_000_000L;
            long nanos = nowNanos - startNanos;
            result = Utils.formatBanyaTime(template, nanos);

        } else {

            final String template = settingService.getString(TEXT_HOWMUCH_AFTER);
            long nowNanos = now.toEpochMilli() * 1_000_000L + System.nanoTime() % 1_000_000L;
            long endNanos = banya.getFinish().toEpochMilli() * 1_000_000L;
            long nanos = nowNanos - endNanos;
            result = Utils.formatBanyaTime(template, nanos);

        }

        sender.sendText(request.getChatId(), result);
    }

    private Long getBanyaChatId(Request request) {
        if (request.isBotChat()) {
            return settingService.getActiveChatId();
        }
        return request.getChatId();
    }

    private static boolean isShowFuck(Request request) {
        return !request.isSuperUser() && (Utils.randomInt(100) == 1);
    }
}
