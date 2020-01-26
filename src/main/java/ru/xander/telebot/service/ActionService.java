package ru.xander.telebot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.xander.telebot.action.Action;
import ru.xander.telebot.action.AdminChatInfoAction;
import ru.xander.telebot.action.AdminDocumentAction;
import ru.xander.telebot.action.AdminIlyaAction;
import ru.xander.telebot.action.AdminKiryaAction;
import ru.xander.telebot.action.AdminOmenAction;
import ru.xander.telebot.action.AdminPhotoAction;
import ru.xander.telebot.action.AdminSendAction;
import ru.xander.telebot.action.AdminSetParamAction;
import ru.xander.telebot.action.AdminSetUserCityAction;
import ru.xander.telebot.action.AdminStickerAction;
import ru.xander.telebot.action.AdminSysParamAction;
import ru.xander.telebot.action.AdminSystemAction;
import ru.xander.telebot.action.AdminUserInfoAction;
import ru.xander.telebot.action.AdminVideoAction;
import ru.xander.telebot.action.DobroAction;
import ru.xander.telebot.action.ExceptionActon;
import ru.xander.telebot.action.FirstTimeAction;
import ru.xander.telebot.action.HappyBirthDayAction;
import ru.xander.telebot.action.HelloActiton;
import ru.xander.telebot.action.HelpAction;
import ru.xander.telebot.action.HowMuchAction;
import ru.xander.telebot.action.IlyaAddAction;
import ru.xander.telebot.action.IlyaRandomAction;
import ru.xander.telebot.action.KiryaRandomAction;
import ru.xander.telebot.action.PictureAction;
import ru.xander.telebot.action.SetBanyaTimeAction;
import ru.xander.telebot.action.SetPictureAction;
import ru.xander.telebot.action.UnknownAction;
import ru.xander.telebot.action.UnsetBanyaTimeAction;
import ru.xander.telebot.action.WeatherAction;
import ru.xander.telebot.dto.Request;
import ru.xander.telebot.util.Sender;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alexander Shakhov
 */
@Slf4j
@Service
public class ActionService {

    @Value("${telegram.bot.userId}")
    private Integer botUserId;
    @Value("${telegram.bot.chatId}")
    private Long botChatId;
    @Value("${telegram.bot.superUserId}")
    private Integer botSuperUserId;

    private final Map<Class<? extends Action>, Action> actionMap;

    @Autowired
    public ActionService(List<Action> actionList) {
        log.info("Found {} actions", actionList.size());
        this.actionMap = actionList.stream().collect(Collectors.toMap(Action::getClass, a -> a));
    }

    public void process(Update update, Sender sender) {
        try {
            Action action = resolveAction(update);
            if (action != null) {
                log.debug("Execute action {}", action.getClass().getSimpleName());
                Request request = prepareRequest(update);
                action.execute(request, sender);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ((ExceptionActon) actionMap.get(ExceptionActon.class))
                    .setException(e)
                    .execute(prepareRequest(update), sender);
        }
    }

    private Action resolveAction(Update update) {
        Message message = update.getMessage();
        if (isFirstTime(message)) {
            return actionMap.get(FirstTimeAction.class);
        }
        if (message.getChatId().equals(botChatId)) {
            Action adminAction = resolveAdminAction(message);
            if (adminAction != null) {
                return adminAction;
            }
        }
        return resolveUserAction(message);
    }

    private Action resolveAdminAction(Message message) {
        if (message.getSticker() != null) {
            return actionMap.get(AdminStickerAction.class);
        }
        if (message.getVideo() != null) {
            return actionMap.get(AdminVideoAction.class);
        }
        if (message.getDocument() != null) {
            return actionMap.get(AdminDocumentAction.class);
        }
        List<PhotoSize> photo = message.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            return actionMap.get(AdminPhotoAction.class);
        }
        final String actionName = prepareActionName(message.getText());
        if (actionName.startsWith("/ilya_")) {
            return actionMap.get(AdminIlyaAction.class);
        } else if (actionName.startsWith("/kirya_")) {
            return actionMap.get(AdminKiryaAction.class);
        } else if (actionName.startsWith("/st ")
                || actionName.startsWith("/st_html")
                || actionName.startsWith("/st_mark")
                || actionName.startsWith("/ss_")
                || actionName.startsWith("/sp_")
                || actionName.startsWith("/sv_")
                || actionName.startsWith("/sd_")) {
            return actionMap.get(AdminSendAction.class);
        } else if (actionName.startsWith("/setusercity")) {
            return actionMap.get(AdminSetUserCityAction.class);
        }
        switch (actionName) {
            case "/chatinfo":
                return actionMap.get(AdminChatInfoAction.class);
            case "/happybyozday_test":
                return actionMap.get(HappyBirthDayAction.class);
            case "/omen":
                return actionMap.get(AdminOmenAction.class);
            case "/setparam":
                return actionMap.get(AdminSetParamAction.class);
            case "/sys":
            case "/system":
                return actionMap.get(AdminSystemAction.class);
            case "/sysparam":
            case "/sysdefault":
                return actionMap.get(AdminSysParamAction.class);
            case "/userinfo":
                return actionMap.get(AdminUserInfoAction.class);
        }
        return null;
    }

    private Action resolveUserAction(Message message) {
        if (message.getVideo() != null) {
            return actionMap.get(SetPictureAction.class);
        }
        if (message.getDocument() != null) {
            return actionMap.get(SetPictureAction.class);
        }
        List<PhotoSize> photo = message.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            return actionMap.get(SetPictureAction.class);
        }
        final String actionName = prepareActionName(message.getText());
        switch (actionName) {
            case "/add_ilya":
                return actionMap.get(IlyaAddAction.class);
            case "/dobro":
                return actionMap.get(DobroAction.class);
            case "/happybyozday":
                return actionMap.get(HappyBirthDayAction.class);
            case "/hello":
                return actionMap.get(HelloActiton.class);
            case "/help":
                return actionMap.get(HelpAction.class);
            case "/howmuch":
                return actionMap.get(HowMuchAction.class);
            case "/pikcha":
                return actionMap.get(PictureAction.class);
            case "/random_ilya":
                return actionMap.get(IlyaRandomAction.class);
            case "/random_kirya":
            case "/kirushizm":
                return actionMap.get(KiryaRandomAction.class);
            case "/set":
                return actionMap.get(SetBanyaTimeAction.class);
            case "/unset":
                return actionMap.get(UnsetBanyaTimeAction.class);
            case "/weather":
                return actionMap.get(WeatherAction.class);
        }
        return actionMap.get(UnknownAction.class);
    }

    private boolean firstTimeChecked = false;

    private boolean isFirstTime(Message message) {
        if (firstTimeChecked) {
            return true;
        }
        firstTimeChecked = true;
        if (message.getNewChatMembers() != null) {
            for (User newUser : message.getNewChatMembers()) {
                if (newUser.getId().equals(botUserId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Request prepareRequest(Update update) {
        final Message message = update.getMessage();
        Request request = new Request();
        request.setMessage(message);
        request.setRawMessage(message.getText());
        request.setActionName(prepareActionName(message.getText()));
        request.setBotUserId(botUserId);
        request.setBotChatId(botChatId);
        request.setSuperUserId(botSuperUserId);
        return request;
    }

    private String prepareActionName(String text) {
        int firstSpace = text.indexOf(' ');
        if (firstSpace > 0) {
            return text.substring(0, firstSpace).trim().toLowerCase();
        }
        return text.trim().toLowerCase();
    }
}
