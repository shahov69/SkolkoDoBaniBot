package ru.xander.telebot.action;

import org.springframework.stereotype.Component;
import ru.xander.telebot.crown.Crown;
import ru.xander.telebot.crown.CrownExtractor;
import ru.xander.telebot.crown.CrownRenderer;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.sender.Sender;

import java.io.InputStream;

/**
 * @author Alexander Shakhov
 */
@Component
public class CrownAction implements Action {
    private final CrownRenderer renderer = new CrownRenderer();
    private final CrownExtractor extractor = new CrownExtractor();
    @Override
    public void execute(Request request, Sender sender) {
        Crown crown = extractor.extract();
        InputStream crownRender = renderer.render(crown, 1);
        sender.sendPicture(request.getChatId(), "crown", crownRender);
    }
}
