package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.crown.Crown;
import ru.xander.telebot.crown.CrownExtractor;
import ru.xander.telebot.crown.CrownRenderer;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.SettingService;

import java.io.InputStream;

/**
 * @author Alexander Shakhov
 */
@Component
public class CrownAction implements Action {

    private final SettingService settingService;
    private final CrownRenderer renderer = new CrownRenderer();
    private final CrownExtractor extractor = new CrownExtractor();

    @Autowired
    public CrownAction(SettingService settingService) {
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        Integer crownLimit = settingService.getInt(SettingName.CROWN_LIMIT);
        renderer.setVisibleRows(crownLimit == null ? CrownRenderer.DEFAULT_CROWN_LIMIT : crownLimit);

        int offset = getOffset(request);

        Crown crown = extractor.extract();
        InputStream crownRender = renderer.render(crown, offset);
        if (offset < 1) {
            sender.sendDocument(request.getChatId(), "crown", crownRender);
        } else {
            sender.sendPicture(request.getChatId(), "crown", crownRender);
        }
    }

    private int getOffset(Request request) {
        try {
            String offset = request.getText().trim();
            if (offset.isEmpty()) {
                return 1;
            }
            return Integer.parseInt(offset);
        } catch (Exception e) {
            return 1;
        }
    }
}
