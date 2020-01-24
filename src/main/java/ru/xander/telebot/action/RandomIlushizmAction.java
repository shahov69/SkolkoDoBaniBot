package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.Ilushizm;
import ru.xander.telebot.repository.IlushizmRepo;
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
public class RandomIlushizmAction implements Action {
    private final IlushizmRepo ilushizmRepo;
    private final SettingService settingService;

    @Autowired
    public RandomIlushizmAction(IlushizmRepo ilushizmRepo, SettingService settingService) {
        this.ilushizmRepo = ilushizmRepo;
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isSuperUser() || settingService.getBoolean(SettingName.ENABLE_ILUSHIZM)) {
            Ilushizm ilushizm = ilushizmRepo.findRandom();
            if (ilushizm == null) {
                sender.sendText(request.getChatId(), "ноль илюшизмов");
            } else {
                try {
                    PosterRenderer renderer = new PosterRenderer();
                    Poster poster = Poster.getRandomIlyaPoster();
                    InputStream picture = renderer.render(poster, ilushizm.getText());
                    sender.sendPicture(request.getChatId(), "ilushizm_" + ilushizm.getId(), picture);
                } catch (Exception e) {
                    sender.sendText(request.getChatId(), ilushizm.getText().concat(" ©"));
                    sender.sendText(request.getBotChatId(), Utils.stackTraceToString(e));
                }
            }
        } else {
            sender.sendText(request.getChatId(), "илюшизмы запрещены! иди нахуй");
            sender.sendSticker(request.getChatId(), "CAADBQADfAMAAukKyAPfAAFRgAuYdNoC");
        }
    }
}
