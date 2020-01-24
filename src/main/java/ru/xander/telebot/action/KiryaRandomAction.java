package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.Kirushizm;
import ru.xander.telebot.repository.KirushizmRepo;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.shizm.Poster;
import ru.xander.telebot.shizm.PosterRenderer;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

import java.io.InputStream;

/**
 * @author Alexander Shakhov
 */
@Component
public class KiryaRandomAction implements Action {
    private final KirushizmRepo kirushizmRepo;
    private final SettingService settingService;

    @Autowired
    public KiryaRandomAction(KirushizmRepo kirushizmRepo, SettingService settingService) {
        this.kirushizmRepo = kirushizmRepo;
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isSuperUser() || settingService.getBoolean(SettingName.ENABLE_KIRUSHIZM)) {
            Kirushizm kirushizm = kirushizmRepo.findRandom();
            if (kirushizm == null) {
                sender.sendText(request.getChatId(), "ноль кирюшизмов");
            } else {
                try {
                    PosterRenderer renderer = new PosterRenderer();
                    Poster poster = Poster.getKiryaPoster();
                    InputStream picture = renderer.render(poster, kirushizm.getText());
                    sender.sendPicture(request.getChatId(), "kirushizm_" + kirushizm.getId(), picture);
                } catch (Exception e) {
                    sender.sendSticker(request.getChatId(), "CAADBQADfAMAAukKyAPfAAFRgAuYdNoC");
                    sender.sendText(request.getBotChatId(), Utils.stackTraceToString(e));
                }
            }
        } else {
            sender.sendText(request.getChatId(), "кирюшизмы запрещены! иди нахуй");
            sender.sendSticker(request.getChatId(), "CAADBQADfAMAAukKyAPfAAFRgAuYdNoC");
        }
    }
}
