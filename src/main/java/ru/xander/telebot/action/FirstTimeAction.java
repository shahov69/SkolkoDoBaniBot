package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Banya;
import ru.xander.telebot.service.BanyaService;
import ru.xander.telebot.util.Sender;

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

        String message = String.format(
                "Bot installed to chat %d (%s)\nCreated banya instance with id = %d",
                request.getChatId(), request.getChatTitle(), banya.getId());
        sender.sendText(request.getBotChatId(), message);
    }
}
