package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.dto.SettingName;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.BanyaService;

/**
 * @author Alexander Shakhov
 */
@Component
public class FirstTimeAction implements Action {
    private final BanyaService banyaService;

    @Autowired
    public FirstTimeAction(BanyaService banyaService) {
        this.banyaService = banyaService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        Banya banya = new Banya();
        banya.setChatId(request.getChatId());
        banya.setChatName(request.getChatTitle());
        banya = banyaService.save(banya);

        String message = "" +
                "Bot installed to chat " + request.getChatId() + " (" + request.getChatTitle() + ")\n" +
                "Created banya instance with id = " + banya.getId() + '\n' +
                "/setparam " + SettingName.ACTIVE_CHAT_ID + ' ' + request.getChatId();
        sender.sendText(request.getBotChatId(), message);
    }
}
