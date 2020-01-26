package ru.xander.telebot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.notification.NotifyData;
import ru.xander.telebot.notification.NotifyEvent;
import ru.xander.telebot.notification.NotifyHandler;
import ru.xander.telebot.repository.BanyaRepo;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Alexander Shakhov
 */
@Service
public class NotifyService {

    /**
     * Небольшая коррекция, чтобы напоминалки отправлялись чуть раньше
     * дабы компенсировать затраты на отправку сообщения в чат
     */
    private static final long CORRECTION = 700L;
    private final Timer timer;
    private final BanyaRepo banyaRepo;
    private final SettingService settingService;

    @Autowired
    public NotifyService(BanyaRepo banyaRepo, SettingService settingService) {
        this.banyaRepo = banyaRepo;
        this.settingService = settingService;
        this.timer = new Timer();
    }

    public void schedule(Sender sender) {
        String howMuchTemplate = settingService.getString(SettingName.TEXT_HOWMUCH_BEFORE);
        this.banyaRepo.findByStartAfter(Utils.now()).forEach(banya -> {
            for (NotifyEvent notifyEvent : NotifyEvent.getAllEvent()) {
                scheduleNotify(banya, notifyEvent, sender, howMuchTemplate);
            }
        });
    }

    private void scheduleNotify(Banya banya, NotifyEvent notifyEvent, Sender sender, String howMuchTemplate) {
        long banyaStart = banya.getStart().toEpochMilli();
        long currentTime = System.currentTimeMillis();
        long delay = banyaStart - currentTime - notifyEvent.getLag() + CORRECTION;
        if ((delay <= 0) || (delay > 48 * 3600 * 1000L)) {
            // если время до евента уже прошло
            // или до него больше 2х дней, то нотифайер не напрягаем лишней работой
            // лимит 2 дня взят из-за того, что бот в Хероку рестартует каждый день
            // так что дальние евенты обязательно добавятся позже. нет смысла их сейчас добавлять
            return;
        }
        timer.schedule(new NotifyTask(sender, banya.getChatId(), howMuchTemplate, notifyEvent), delay);
    }

    @AllArgsConstructor
    private static class NotifyTask extends TimerTask {

        private final Sender sender;
        private final Long chatId;
        private final String howMuchTemplate;
        private final NotifyEvent notifyEvent;

        @Override
        public void run() {
            long nanos = notifyEvent.getLag() * 1_000_000L;
            NotifyHandler notifyHandler = notifyEvent.getHandler();
            notifyHandler.handle(sender, new NotifyData(chatId, howMuchTemplate, nanos, true));
        }
    }

}
