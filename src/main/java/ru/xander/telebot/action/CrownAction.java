package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.crown.CrownExtractor;
import ru.xander.telebot.crown.CrownInfo;
import ru.xander.telebot.crown.CrownRenderer;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.CrownService;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.util.Utils;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * @author Alexander Shakhov
 */
@Component
public class CrownAction implements Action {

    private final SettingService settingService;
    private final CrownService crownService;
    private final CrownRenderer renderer = new CrownRenderer();

    @Autowired
    public CrownAction(SettingService settingService, CrownService crownService) {
        this.settingService = settingService;
        this.crownService = crownService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        int offset;
        String[] args = request.getArgs();
        if (args.length > 0) {
            try {
                offset = Integer.parseInt(args[0]);
            } catch (Exception e) {
                offset = 1;
            }
        }

        boolean toUpdate = false;
        if (args.length > 1) {
            toUpdate = "+".equals(args[1]);
        }

        LocalDate crownDay = settingService.getLocalDate(SettingName.CROWN_DAY);
        LocalDate now = LocalDate.now(Utils.ZONE_ID_MOSCOW);
        if ((crownDay == null) || (crownDay.compareTo(now) < 0) || toUpdate) {
            crownService.update(new CrownExtractor().extract());
            sender.sendText(request.getBotChatId(), "информация по короне обновлена");
        }

        offset = getOffset(request);
        String filename = "crown_" + Utils.formatDate(Utils.now(), "yyyyMMdd_hhmmss") + ".png";

        CrownInfo crown = crownService.getCrownInfo();

        Integer crownLimit = settingService.getInt(SettingName.CROWN_LIMIT);
        renderer.setVisibleRows(crownLimit == null ? CrownRenderer.DEFAULT_CROWN_LIMIT : crownLimit);
        InputStream crownRender = renderer.render(crown, offset);
        if (offset < 1) {
            sender.sendDocument(request.getChatId(), filename, crownRender);
        } else {
            sender.sendPicture(request.getChatId(), filename, crownRender);
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
