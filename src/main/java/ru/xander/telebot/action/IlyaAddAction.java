package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Ilushizm;
import ru.xander.telebot.repository.IlushizmRepo;
import ru.xander.telebot.util.Sender;
import ru.xander.telebot.util.Utils;

/**
 * @author Alexander Shakhov
 */
@Component
public class IlyaAddAction implements Action {
    private final IlushizmRepo ilushizmRepo;

    @Autowired
    public IlyaAddAction(IlushizmRepo ilushizmRepo) {
        this.ilushizmRepo = ilushizmRepo;
    }

    @Override
    public void execute(Request request, Sender sender) {
        String text = request.getText().trim();
        if (text.isEmpty()) {
            sender.sendText(request.getChatId(), "не балуй, чудик!");
            return;
        }

        try {
            Ilushizm ilushizm = new Ilushizm();
            ilushizm.setText(text);
            ilushizm.setCreator(request.getUserFullName());
            ilushizm.setCreated(Utils.now());
            ilushizm.setAccepted(request.isSuperUser());
            Ilushizm saved = ilushizmRepo.save(ilushizm);

            if (request.isSuperUser()) {
                sender.sendText(request.getChatId(), "Илюшизм успешно добавлен");
            } else {
                sender.sendText(request.getChatId(), "Илюшизм успешно добавлен, но мне нужно согласовать это действие с Сашкой");
            }

            String adminMessage = saved.getText() + "<code>by " + saved.getCreator() + "</code>\n"
                    + (request.isSuperUser() ? Utils.EMPTY_STRING : "/ilya_acc_" + saved.getId() + "\n")
                    + "/ilya_del_" + saved.getId() + "\n"
                    + "/ilya_test_" + saved.getId() + "\n"
                    + "/ilya_send_" + saved.getId();
            sender.sendText(request.getBotChatId(), adminMessage);
        } catch (Exception e) {
            sender.sendText(request.getChatId(), "Чот сломался я. Починить бы меня");
            sender.sendText(request.getBotChatId(), Utils.stackTraceToString(e));
        }
    }
}
