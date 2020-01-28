package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Kirushizm;
import ru.xander.telebot.repository.KirushizmRepo;
import ru.xander.telebot.sender.Sender;
import ru.xander.telebot.service.SettingService;
import ru.xander.telebot.shizm.Poster;
import ru.xander.telebot.shizm.PosterRenderer;
import ru.xander.telebot.util.Utils;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexander Shakhov
 */
@Component
public class AdminKiryaAction implements Action {

    private final KirushizmRepo kirushizmRepo;
    private final SettingService settingService;

    @Autowired
    public AdminKiryaAction(KirushizmRepo kirushizmRepo, SettingService settingService) {
        this.kirushizmRepo = kirushizmRepo;
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String[] params = request.getActionName().split("_");
            switch (params[1]) {
                case "add":
                    addKirushizm(request, sender);
                    break;
                case "del":
                    deleteKirushizm(params, request, sender);
                    break;
                case "filter":
                    filterKirushizm(request, sender);
                    break;
                case "news":
                    newsKirushizms(request, sender);
                    break;
                case "send":
                    sendKirushizm(params, request, sender);
                    break;
                case "test":
                    testKirushizm(params, request, sender);
                    break;
                default:
                    sender.sendText(request.getBotChatId(), "чото непонятное '" + request.getActionName() + "'");
                    break;
            }
        }
    }

    private void addKirushizm(Request request, Sender sender) {
        String text = request.getText();
        if (text.isEmpty()) {
            sender.sendText(request.getBotChatId(), "не балуй, чудик!");
            return;
        }

        Kirushizm kirushizm = new Kirushizm();
        kirushizm.setText(text);
        kirushizm.setCreator(request.getUserFullName());
        kirushizm.setCreated(Utils.now());
        kirushizm.setAccepted(request.isSuperUser());
        Kirushizm saved = kirushizmRepo.save(kirushizm);

        String adminMessage = saved.getText() + '\n' +
                "<code>by " + saved.getCreator() + "</code>\n"
                + "/kirya_del_" + saved.getId() + '\n'
                + "/kirya_test_" + saved.getId() + '\n'
                + "/kirya_send_" + saved.getId();
        sender.sendText(request.getBotChatId(), adminMessage, MessageMode.HTML);
    }

    private void deleteKirushizm(String[] params, Request request, Sender sender) {
        if (params.length < 3) {
            sender.sendText(request.getBotChatId(), "нужно указать id кирюшизма");
            return;
        }
        long id = Long.parseLong(params[2]);
        kirushizmRepo.deleteById(id);
        sender.sendText(request.getBotChatId(), "кирюшизм " + id + " удален!");
    }

    private void filterKirushizm(Request request, Sender sender) {
        String filterText = request.getText();
        if (filterText.isEmpty()) {
            sender.sendText(request.getBotChatId(), "текст фильтра не может быть пустым");
            return;
        }
        List<Kirushizm> kirushizms = kirushizmRepo.findByText(filterText);
        if (kirushizms.isEmpty()) {
            sender.sendText(request.getBotChatId(), "кирюшизмы не найдены (txt like '%" + filterText + "%')");
            return;
        }
        sendKirushizms(request, sender, kirushizms);
    }

    private void newsKirushizms(Request request, Sender sender) {
        String text = request.getText();
        if (text.isEmpty()) {
            sender.sendText(request.getBotChatId(), "не балуй, чудик!");
            return;
        }
        Long activeChatId = settingService.getActiveChatId();
        sendKirushizmToChat(text, activeChatId, sender);
    }

    private void sendKirushizm(String[] params, Request request, Sender sender) {
        Kirushizm kirushizm = findKirushizmByParam(params, request, sender);
        if (kirushizm == null) {
            return;
        }
        Long activeChatId = settingService.getActiveChatId();
        sendKirushizmToChat(kirushizm.getText(), activeChatId, sender);
    }

    private void testKirushizm(String[] params, Request request, Sender sender) {
        Kirushizm kirushizm = findKirushizmByParam(params, request, sender);
        if (kirushizm == null) {
            return;
        }
        sendKirushizmToChat(kirushizm.getText(), request.getBotChatId(), sender);
    }

    private void sendKirushizms(Request request, Sender sender, List<Kirushizm> kirushizms) {
        StringBuilder out = new StringBuilder();
        for (Kirushizm kirushizm : kirushizms) {
            String kirushizmInfo = kirushizm.getText() + '\n'
                    + "<code>by " + kirushizm.getCreator() + "</code>\n"
                    + "/kirya_del_" + kirushizm.getId() + '\n'
                    + "/kirya_test_" + kirushizm.getId() + '\n'
                    + "/kirya_send_" + kirushizm.getId();
            if ((out.length() + kirushizmInfo.length()) > Sender.MAX_MESSAGE_LENGTH) {
                sender.sendText(request.getBotChatId(), out.toString());
                out.setLength(0);
            }
            out.append(kirushizmInfo);
        }
        if (out.length() > 0) {
            sender.sendText(request.getBotChatId(), out.toString(), MessageMode.HTML);
        }
    }

    private Kirushizm findKirushizmByParam(String[] params, Request request, Sender sender) {
        if (params.length < 3) {
            sender.sendText(request.getBotChatId(), "нужно указать id кирюшизма");
            return null;
        }
        long id = Long.parseLong(params[2]);
        Optional<Kirushizm> optional = kirushizmRepo.findById(id);
        if (!optional.isPresent()) {
            sender.sendText(request.getBotChatId(), "кирюшизм с id = " + id + " не найден");
            return null;
        }
        return optional.get();
    }

    private void sendKirushizmToChat(String kirushizm, Long chatId, Sender sender) {
        Poster poster = Poster.getKiryaPoster();
        PosterRenderer renderer = new PosterRenderer();
        InputStream picture = renderer.render(poster, kirushizm);
        sender.sendPicture(chatId, "kirushizm", picture);
    }
}
