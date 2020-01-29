package ru.xander.telebot.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.xander.telebot.dto.MessageMode;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.entity.Ilushizm;
import ru.xander.telebot.repository.IlushizmRepo;
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
public class AdminIlyaAction implements Action {
    private final IlushizmRepo ilushizmRepo;
    private final SettingService settingService;

    @Autowired
    public AdminIlyaAction(IlushizmRepo ilushizmRepo, SettingService settingService) {
        this.ilushizmRepo = ilushizmRepo;
        this.settingService = settingService;
    }

    @Override
    public void execute(Request request, Sender sender) {
        if (request.isBotChat()) {
            String[] params = request.getActionName().split("_");
            switch (params[1]) {
                case "acc":
                    acceptIlushizm(params, request, sender);
                    break;
                case "del":
                    deleteIlushizm(params, request, sender);
                    break;
                case "filter":
                    filterIlushizm(request, sender);
                    break;
                case "send":
                    sendIlushizm(params, request, sender);
                    break;
                case "test":
                    testIlushizm(params, request, sender);
                    break;
                case "unaccepted":
                    unacceptedIlushizms(request, sender);
                    break;
                default:
                    sender.sendText(request.getBotChatId(), "чото непонятное '" + request.getActionName() + "'");
                    break;
            }
        }
    }

    private void acceptIlushizm(String[] params, Request request, Sender sender) {
        Ilushizm ilushizm = findIlushizmByParam(params, request, sender);
        if (ilushizm == null) {
            return;
        }
        ilushizm.setAccepted(true);
        ilushizmRepo.save(ilushizm);
        sender.sendText(request.getBotChatId(), "илюшизм " + ilushizm.getId() + " аксептед!");
    }

    private void deleteIlushizm(String[] params, Request request, Sender sender) {
        if (params.length < 3) {
            sender.sendText(request.getBotChatId(), "нужно указать id илюшизма");
            return;
        }
        long id = Long.parseLong(params[2]);
        ilushizmRepo.deleteById(id);
        sender.sendText(request.getBotChatId(), "илюшизм " + id + " удален!");
    }

    private void filterIlushizm(Request request, Sender sender) {
        String filterText = request.getText();
        if (filterText.isEmpty()) {
            sender.sendText(request.getBotChatId(), "текст фильтра не может быть пустым");
            return;
        }
        List<Ilushizm> ilushizms = ilushizmRepo.findByText(filterText);
        if (ilushizms.isEmpty()) {
            sender.sendText(request.getBotChatId(), "илюшизмы не найдены (txt like '%" + filterText + "%')");
            return;
        }
        sendIlushizms(request, sender, ilushizms);
    }

    private void sendIlushizm(String[] params, Request request, Sender sender) {
        Ilushizm ilushizm = findIlushizmByParam(params, request, sender);
        if (ilushizm == null) {
            return;
        }
        Long activeChatId = settingService.getActiveChatId();
        sendIlushizmToChat(ilushizm, activeChatId, sender, params);
    }

    private void testIlushizm(String[] params, Request request, Sender sender) {
        Ilushizm ilushizm = findIlushizmByParam(params, request, sender);
        if (ilushizm == null) {
            return;
        }
        sendIlushizmToChat(ilushizm, request.getBotChatId(), sender, params);
    }

    private void unacceptedIlushizms(Request request, Sender sender) {
        List<Ilushizm> ilushizms = ilushizmRepo.findUnaccepted();
        if (ilushizms.isEmpty()) {
            sender.sendText(request.getBotChatId(), "ноль неакцептнутых илюшизмов");
            return;
        }
        sendIlushizms(request, sender, ilushizms);
    }

    private Ilushizm findIlushizmByParam(String[] params, Request request, Sender sender) {
        if (params.length < 3) {
            sender.sendText(request.getBotChatId(), "нужно указать id илюшизма");
            return null;
        }
        long id = Long.parseLong(params[2]);
        Optional<Ilushizm> optional = ilushizmRepo.findById(id);
        if (!optional.isPresent()) {
            sender.sendText(request.getBotChatId(), "илюшизм с id = " + id + " не найден");
            return null;
        }
        return optional.get();
    }

    private void sendIlushizms(Request request, Sender sender, List<Ilushizm> ilushizms) {
        StringBuilder out = new StringBuilder();
        for (Ilushizm ilushizm : ilushizms) {
            String ilushizmInfo = ilushizm.getText() + '\n' +
                    "<code>by " + ilushizm.getCreator() + "</code>\n"
                    + (ilushizm.getAccepted() ? Utils.EMPTY_STRING : "/ilya_acc_" + ilushizm.getId() + "\n")
                    + "/ilya_del_" + ilushizm.getId() + '\n'
                    + "/ilya_test_" + ilushizm.getId() + '\n'
                    + "/ilya_send_" + ilushizm.getId() + '\n';
            if ((out.length() + ilushizmInfo.length()) > Sender.MAX_MESSAGE_LENGTH) {
                sender.sendText(request.getBotChatId(), out.toString());
                out.setLength(0);
            }
            out.append(ilushizmInfo);
        }
        if (out.length() > 0) {
            sender.sendText(request.getBotChatId(), out.toString(), MessageMode.HTML);
        }
    }

    private void sendIlushizmToChat(Ilushizm ilushizm, Long chatId, Sender sender, String[] params) {
        Poster poster;
        if (params.length > 3) {
            poster = Poster.getIlyaPoster(Integer.parseInt(params[3]));
        } else {
            poster = Poster.getRandomIlyaPoster();
        }
        PosterRenderer renderer = new PosterRenderer();
        InputStream picture = renderer.render(poster, ilushizm.getText());
        sender.sendPicture(chatId, "ilushizm_" + ilushizm.getId(), picture);
    }

}
