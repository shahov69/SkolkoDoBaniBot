package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.service.BanyaService;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

/**
 * @author Alexander Shakhov
 */
@Component
public class PictureAction implements Action {
    private final BanyaService banyaService;

    @Autowired
    public PictureAction(BanyaService banyaService) {
        this.banyaService = banyaService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        Banya banya = banyaService.getBanyaForActiveChat(request);

        if ((banya == null) || StringUtils.isEmpty(banya.getPicture())) {
            Utils.tryWithResource(
                    "/media/default_picture.jpg",
                    picture -> sender.sendPicture(request.getChatId(), "пикча для бани", picture));
        } else {
            String pictureId = banya.getPicture();
            String contentId = pictureId.substring(2);
            switch (pictureId.charAt(0)) {
                case 'p':
                    sender.sendPicture(request.getChatId(), contentId);
                    break;
                case 'v':
                    sender.sendVideo(request.getChatId(), contentId);
                    break;
                case 'd':
                    sender.sendDocument(request.getChatId(), contentId);
                    break;
                case 's':
                    sender.sendSticker(request.getChatId(), contentId);
                    break;
                case 't':
                    sender.sendText(request.getChatId(), contentId);
                    break;
                default:
                    sender.sendText(request.getChatId(), "хз чо");
                    break;
            }
        }
    }
}
